package com.durys.jakub.configurationservice.moduleconfiguration.application.handler

import com.durys.jakub.configurationservice.module.domain.events.ModuleConfigurationPatternChanged
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class ModuleConfigurationPatternChangedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    @EventListener
    fun handle(event: ModuleConfigurationPatternChanged) {

        val moduleConfigurations = moduleConfigurationRepository.moduleConfigurations(event.module)
                .map { it.updateConfigurations(event.patterns)}

        moduleConfigurationRepository.saveAll(moduleConfigurations)
    }

}