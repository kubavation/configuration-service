package com.durys.jakub.configurationservice.context.application

import com.durys.jakub.configurationservice.context.domain.Context
import com.durys.jakub.configurationservice.context.domain.ContextModule
import com.durys.jakub.configurationservice.context.domain.ContextValidationService
import com.durys.jakub.configurationservice.context.domain.event.ContextModulesChangedEvent
import com.durys.jakub.configurationservice.context.domain.exception.ContextAlreadyExistsException
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextDTO
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextModuleDTO
import com.durys.jakub.configurationservice.events.DomainEventPublisher
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
internal class ContextApplicationService(private val contextRepository: ContextRepository,
                                         private val contextValidator: ContextValidationService,
                                         private val eventPublisher: DomainEventPublisher)  {

    fun setContextModules(context: String, modules: List<ContextModuleDTO>) {

        val entity = contextRepository.findByName(context)
                .map { Context(it.id, it.name, modules.map {module -> ContextModule(module.name) }) }
                .map { contextRepository.save(it) }
                .orElseThrow { EntityNotFoundException(context) }

        eventPublisher
                .publish(ContextModulesChangedEvent(entity.name, entity.modules.map { it.moduleName }))
    }

    fun create(context: ContextDTO) {
        if (contextValidator.contextWithNameExists(context.name)) {
            throw ContextAlreadyExistsException(context.name)
        }

        contextRepository.save(Context(context.name, emptyList()))
    }

    fun edit(contextName: String, context: ContextDTO) {
        if (contextValidator.contextWithNameExists(context.name)) {
            throw ContextAlreadyExistsException(context.name)
        }

        contextRepository.findByName(contextName)
                .map { Context(it.id, contextName, it.modules) }
                .map { contextRepository.save(it) }
                .orElseThrow { EntityNotFoundException(contextName) }
    }

    fun delete(contextName: String) {
        contextRepository.findByName(contextName)
                .ifPresent { contextRepository.delete(it) }
    }
}