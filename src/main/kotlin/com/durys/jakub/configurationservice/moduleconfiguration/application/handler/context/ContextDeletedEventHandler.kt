package com.durys.jakub.configurationservice.moduleconfiguration.application.handler.context

import com.durys.jakub.configurationservice.context.domain.event.ContextDeletedEvent
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class ContextDeletedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    @EventListener
    @Transactional
    fun handle(event: ContextDeletedEvent) {
        moduleConfigurationRepository.deleteAll(moduleConfigurationRepository.contextModuleConfigurations(event.context))
    }

}
