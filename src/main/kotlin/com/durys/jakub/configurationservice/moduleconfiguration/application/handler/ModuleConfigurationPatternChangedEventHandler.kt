package com.durys.jakub.configurationservice.moduleconfiguration.application.handler

import com.durys.jakub.configurationservice.module.domain.events.ModuleConfigurationPatternChanged
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
internal class ModuleConfigurationPatternChangedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    @EventListener
    fun handle(event: ModuleConfigurationPatternChanged) {

        logger.info { "handling ModuleConfigurationPatternChanged event | module = ${event.module}, patterns size = ${event.patterns.size}" }

        val moduleConfigurations = moduleConfigurationRepository.moduleConfigurations(event.module)
                .map { it.updateConfigurations(event.patterns)}

        moduleConfigurationRepository.saveAll(moduleConfigurations)
    }

}