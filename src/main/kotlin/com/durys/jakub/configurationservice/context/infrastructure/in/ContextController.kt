package com.durys.jakub.configurationservice.context.infrastructure.`in`

import com.durys.jakub.configurationservice.context.application.ContextApplicationService
import com.durys.jakub.configurationservice.context.domain.Context
import com.durys.jakub.configurationservice.context.domain.ContextModule
import com.durys.jakub.configurationservice.context.infrastructure.ContextRepository
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextDTO
import com.durys.jakub.configurationservice.context.infrastructure.model.ContextModuleDTO
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.web.bind.annotation.*

@RequestMapping("/contexts")
@RestController
internal class ContextController(val contextRepository: ContextRepository,
                                 val contextApplicationService: ContextApplicationService) {

    @GetMapping
    fun getAvailableContexts(): List<ContextDTO> {
        return contextRepository.findAll().stream()
                .map { ContextDTO(it.name) }
                .toList()
    }

    @GetMapping("/{context}/modules")
    fun getContextModules(@PathVariable context: String): List<ContextModuleDTO> {
        return contextRepository.findByName(context)
                .map { it.modules.map { module -> ContextModuleDTO(module.moduleName) } }
                .orElse(listOf())
    }

    @PatchMapping("/{context}/modules")
    fun setContextModules(@PathVariable context: String, @RequestBody modules: List<ContextModuleDTO>) {
        contextApplicationService.setContextModules(context, modules)
    }
}