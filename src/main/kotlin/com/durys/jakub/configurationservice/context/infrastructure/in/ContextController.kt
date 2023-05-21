package com.durys.jakub.configurationservice.context.infrastructure.`in`

import com.durys.jakub.configurationservice.context.domain.Context
import com.durys.jakub.configurationservice.context.domain.ContextModule
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextDTO
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextModuleDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
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
    fun getContextModules(@PathVariable context: String): List<ContextModuleDTO> {
        return contextRepository.findById(context)
                .map { it.modules.map { module -> ContextModuleDTO(module.moduleName) } }
                .orElse(listOf())
    }

    @GetMapping("/{context}/modules")
    fun setContextModules(@PathVariable context: String, @RequestBody modules: List<ContextModuleDTO>) {
        val entity = contextRepository.findById(context)
                .map { Context(it.name, modules.map {module -> ContextModule(module.name)}) }
                .orElseThrow { RuntimeException("not found") }
        contextRepository.save(entity)
    }
}