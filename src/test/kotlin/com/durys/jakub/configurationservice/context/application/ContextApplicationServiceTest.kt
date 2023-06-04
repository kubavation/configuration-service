package com.durys.jakub.configurationservice.context.application

import com.durys.jakub.configurationservice.context.domain.Context
import com.durys.jakub.configurationservice.context.domain.ContextValidationService
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextDTO
import com.durys.jakub.configurationservice.events.SpringDomainEventPublisher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito

internal class ContextApplicationServiceTest {

    val contextRepository = Mockito.mock(ContextRepository::class.java)
    val contextValidationService = Mockito.mock(ContextValidationService::class.java)
    val domainEventPublisher = Mockito.mock(SpringDomainEventPublisher::class.java)
    val contextApplicationService = ContextApplicationService(contextRepository, contextValidationService, domainEventPublisher)

    @Test
    fun createContext_shouldSuccessfullySaveContext() {

        val context = Context("Warsaw", emptyList())

        Mockito.`when`(contextRepository.save(context)).thenReturn(context)
        Mockito.`when`(contextValidationService.contextWithNameExists(context.name)).thenReturn(false)

        assertDoesNotThrow {contextApplicationService.create(ContextDTO(context.name))}
    }
}