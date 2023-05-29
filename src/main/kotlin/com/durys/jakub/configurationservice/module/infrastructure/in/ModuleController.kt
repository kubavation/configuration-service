package com.durys.jakub.configurationservice.module.infrastructure.`in`

import com.durys.jakub.configurationservice.events.DomainEventPublisher
import com.durys.jakub.configurationservice.module.domain.Module
import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import com.durys.jakub.configurationservice.module.domain.events.ModuleConfigurationPatternChanged
import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.module.infrastructure.model.ConfigurationPatternDTO
import com.durys.jakub.configurationservice.module.infrastructure.model.ModuleDTO
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/modules")
internal class ModuleController(val moduleRepository: ModuleRepository, val domainEventPublisher: DomainEventPublisher) {

    @GetMapping
    fun getModules(): List<ModuleDTO> = moduleRepository.findAll().map { ModuleDTO(it.name, it.description) }


    @PostMapping
    fun addModule(@RequestBody moduleDTO: ModuleDTO) = moduleRepository.save(Module(moduleDTO.name, moduleDTO.description, emptyList()))

    @PatchMapping("/{name}")
    fun editModule(@PathVariable name: String, @RequestBody moduleDTO: ModuleDTO) {

        val module = moduleRepository.findByName(name)
                .map { Module(it.id, moduleDTO.name, moduleDTO.description, it.configPatterns) }
                .orElseThrow { EntityNotFoundException(name) }
        moduleRepository.save(module)
    }

    @DeleteMapping("/{name}")
    fun deleteModule(@PathVariable name: String) {
        val module = moduleRepository.findByName(name)
                .orElseThrow { EntityNotFoundException(name) }
        moduleRepository.delete(module)
    }


    @GetMapping("/{name}/configuration-pattern")
    fun getModuleConfigurationPattern(@PathVariable name: String): List<ConfigurationPatternDTO> {
       return moduleRepository.findByName(name)
                .map { it.configPatterns.map {pattern -> ConfigurationPatternDTO(pattern.name, pattern.description, pattern.defaultValue) } }
                .orElseThrow { EntityNotFoundException(name) }
    }

    @PatchMapping("/{name}/configuration-pattern")
    fun setModuleConfigurationPatterns(@PathVariable name: String, @RequestBody configPatterns: List<ConfigurationPatternDTO>) {
       val module = moduleRepository.findByName(name)
                .map { it with asConfigPatterns(configPatterns) }
                .map { moduleRepository.save(it) }
                .orElseThrow { EntityNotFoundException(name) }

        domainEventPublisher.publish(ModuleConfigurationPatternChanged(module.name, module.configPatterns))
    }

    @DeleteMapping("/{name}/configuration-pattern/{pattern}")
    fun deleteModuleConfigurationPattern(@PathVariable name: String, @PathVariable pattern: String) {
        val module = moduleRepository.findByName(name)
                .orElseThrow { EntityNotFoundException(name) }
        module.configPatterns = module.configPatterns.filter { it.name != pattern }
        moduleRepository.save(module)

        domainEventPublisher.publish(ModuleConfigurationPatternChanged(module.name, module.configPatterns))
    }

    @PatchMapping("/{name}/configuration-pattern/{pattern}")
    fun patchModuleConfigurationPattern(@PathVariable name: String, @PathVariable pattern: String,
                                        @RequestBody configPattern: ConfigurationPatternDTO) {
        val module = moduleRepository.findByName(name)
                .orElseThrow { EntityNotFoundException(name) }

        module.configPatterns = module.configPatterns.filter { it.name != pattern } + module.configPatterns.filter { it.name == pattern }
                .map { ModuleConfigurationPattern(it.name, configPattern.description, configPattern.defaultValue) }
                .first()

        moduleRepository.save(module)
        domainEventPublisher.publish(ModuleConfigurationPatternChanged(module.name, module.configPatterns))
    }

    private fun asConfigPatterns(configPatterns: List<ConfigurationPatternDTO>): List<ModuleConfigurationPattern> {
       return configPatterns.map {
            pattern -> ModuleConfigurationPattern(pattern.name, pattern.description, pattern.defaultValue) }
    }

}