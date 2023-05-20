package com.durys.jakub.configurationservice.context.infrastructure.`in`

import com.durys.jakub.configurationservice.context.domain.ContextModule
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextDTO
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/contexts")
@RestController
internal class ContextController(val contextRepository: ContextRepository) {

    @GetMapping
    fun getAvailableContexts(): List<ContextDTO> {
        return contextRepository.findAll().stream()
                .map { ContextDTO(it.name) }
                .toList()
    }

    @GetMapping("/{context}/modules")
    fun getContextModules(@PathVariable context: String): List<ContextModule> {
        return contextRepository.findById(context)
                .map { it.modules }
                .orElse(listOf())
    }
}