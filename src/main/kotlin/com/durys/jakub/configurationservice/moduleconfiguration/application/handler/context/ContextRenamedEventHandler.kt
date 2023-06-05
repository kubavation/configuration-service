package com.durys.jakub.configurationservice.moduleconfiguration.application.handler.context

import com.durys.jakub.configurationservice.context.domain.event.ContextRenamedEvent
import com.durys.jakub.configurationservice.moduleconfiguration.domain.ModuleConfiguration
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class ContextRenamedEventHandler(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    @EventListener
    @Transactional
    fun handle(event: ContextRenamedEvent) {
       val configs = moduleConfigurationRepository.contextModuleConfigurations(event.oldContext)
                .map { ModuleConfiguration(it.id, event.newContext, it.module, it.configurations) }
        moduleConfigurationRepository.saveAll(configs)
    }

}
