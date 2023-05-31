package com.durys.jakub.configurationservice.moduleconfiguration.application.handler

import com.durys.jakub.configurationservice.context.domain.event.ContextModulesChangedEvent
import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.moduleconfiguration.domain.Configuration
import com.durys.jakub.configurationservice.moduleconfiguration.domain.ModuleConfiguration
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class ContextModulesChangedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository,
                                                 val moduleRepository: ModuleRepository) {

    @EventListener
    fun handle(event: ContextModulesChangedEvent) {

        event.moduleNames
                .filter { moduleConfigurationRepository.moduleConfiguration(event.context, it) == null }
                .map { moduleRepository.findByName(it) }
                .forEach { module -> module.ifPresent {
                    moduleConfigurationRepository.save(ModuleConfiguration(event.context, it.name,
                            it.configPatterns.map {
                                pattern -> Configuration(pattern.name, pattern.description, pattern.defaultValue)
                            }))
                } }
    }

}
