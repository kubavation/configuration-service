package com.durys.jakub.configurationservice.context.domain

import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import org.springframework.stereotype.Service

@Service
internal class ContextValidationService(val contextRepository: ContextRepository) {

    fun contextWithNameExists(context: String) = contextRepository.findByName(context).isPresent

}