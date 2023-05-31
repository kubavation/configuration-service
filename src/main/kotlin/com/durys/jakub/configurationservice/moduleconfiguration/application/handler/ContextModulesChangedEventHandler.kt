package com.durys.jakub.configurationservice.moduleconfiguration.application.handler

import com.durys.jakub.configurationservice.context.domain.event.ContextModulesChangedEvent
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class ContextModulesChangedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    @EventListener
    fun handle(event: ContextModulesChangedEvent) {

    }

}