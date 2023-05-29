package com.durys.jakub.configurationservice.moduleconfiguration.application

import com.durys.jakub.configurationservice.module.domain.events.ModuleConfigurationPatternChanged
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class ModuleConfigurationEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    @EventListener
    fun handle(event: ModuleConfigurationPatternChanged) {
        
    }

}