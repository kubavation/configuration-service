package com.durys.jakub.configurationservice.moduleconfiguration.application.handler

import com.durys.jakub.configurationservice.module.domain.events.ModuleConfigurationPatternChanged
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import mu.KotlinLogging
import org.springframework.cache.CacheManager
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Component
internal class ModuleConfigurationPatternChangedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository,
                                                             val cacheManager: CacheManager) {

    @EventListener
    @Transactional
    fun handle(event: ModuleConfigurationPatternChanged) {

        logger.info { "handling ModuleConfigurationPatternChanged event | module = ${event.module}, patterns size = ${event.patterns.size}" }

        val moduleConfigurations = moduleConfigurationRepository.moduleConfigurations(event.module)

        val updatedModuleConfigurations = moduleConfigurations.map { it.updateConfigurations(event.patterns)}

        moduleConfigurations.map { it.configurations = emptyList(); return@map it; }
                .forEach { moduleConfigurationRepository.delete(it);
                           cacheManager.getCache("config")?.evict("${it.context},${it.module}")
                }

        moduleConfigurationRepository.saveAll(updatedModuleConfigurations)


    }

}