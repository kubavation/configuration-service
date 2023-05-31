package com.durys.jakub.configurationservice.context.application

import com.durys.jakub.configurationservice.context.domain.Context
import com.durys.jakub.configurationservice.context.domain.ContextModule
import com.durys.jakub.configurationservice.context.domain.event.ContextModulesChangedEvent
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextModuleDTO
import com.durys.jakub.configurationservice.events.DomainEventPublisher
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
internal class ContextApplicationService(private val contextRepository: ContextRepository,
                                         private val eventPublisher: DomainEventPublisher)  {

    fun setContextModules(context: String, modules: List<ContextModuleDTO>) {

        val entity = contextRepository.findByName(context)
                .map { Context(it.id, it.name, modules.map {module -> ContextModule(module.name) }) }
                .map { contextRepository.save(it) }
                .orElseThrow { EntityNotFoundException(context) }

        eventPublisher
                .publish(ContextModulesChangedEvent(entity.name, entity.modules.map { it.moduleName }))
    }
}