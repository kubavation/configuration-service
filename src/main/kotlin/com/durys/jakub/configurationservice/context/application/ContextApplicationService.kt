package com.durys.jakub.configurationservice.context.application

import com.durys.jakub.configurationservice.context.domain.Context
import com.durys.jakub.configurationservice.context.domain.ContextModule
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextModuleDTO
import com.durys.jakub.configurationservice.events.DomainEventPublisher
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
internal class ContextApplicationService(val contextRepository: ContextRepository,
                                         val eventPublisher: DomainEventPublisher)  {

    fun setContextModules(context: String, modules: List<ContextModuleDTO>) {
        val entity = contextRepository.findByName(context)
                .map { Context(it.id, it.name, modules.map {module -> ContextModule(module.name) }) }
                .orElseThrow { EntityNotFoundException(context) }
        contextRepository.save(entity)
    }
}