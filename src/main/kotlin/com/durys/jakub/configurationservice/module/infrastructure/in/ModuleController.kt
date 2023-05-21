package com.durys.jakub.configurationservice.module.infrastructure.`in`

import com.durys.jakub.configurationservice.module.domain.Module
import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.module.infrastructure.model.ConfigurationPatternDTO
import com.durys.jakub.configurationservice.module.infrastructure.model.ModuleDTO
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/modules")
internal class ModuleController(val moduleRepository: ModuleRepository) {

    @GetMapping
    fun getModules(): List<ModuleDTO> = moduleRepository.findAll().map { ModuleDTO(it.name, it.description) }

    @GetMapping("/{name}/configuration-pattern")
    fun getModuleConfigurationPattern(@PathVariable name: String): List<ConfigurationPatternDTO> {
       return moduleRepository.findById(name)
                .map { it.configPatterns.map {pattern -> ConfigurationPatternDTO(pattern.name, pattern.description, pattern.defaultValue) } }
                .orElseThrow { EntityNotFoundException(name) }
    }

    @PatchMapping("/{name}/configuration-pattern")
    fun setModuleConfigurationPatterns(@PathVariable name: String, @RequestBody configPatterns: List<ConfigurationPatternDTO>) {
       val module = moduleRepository.findById(name)
                .map { Module(it.name, it.description, it.configPatterns + to(configPatterns)) }
                .orElseThrow { EntityNotFoundException(name) }
        moduleRepository.save(module)
    }

    @DeleteMapping("/{name}/configuration-pattern/{pattern}")
    fun deleteModuleConfigurationPattern(@PathVariable name: String, @PathVariable pattern: String) {
        val module = moduleRepository.findById(name)
                .orElseThrow { EntityNotFoundException(name) }
        module.configPatterns = module.configPatterns.filter { it.name != pattern }
        moduleRepository.save(module)
    }

    @PatchMapping("/{name}/configuration-pattern/{pattern}")
    fun patchModuleConfigurationPattern(@PathVariable name: String, @PathVariable pattern: String,
                                        @RequestBody configPattern: ConfigurationPatternDTO) {
        val module = moduleRepository.findById(name)
                .orElseThrow { EntityNotFoundException(name) }

        module.configPatterns = module.configPatterns.filter { it.name != pattern } + module.configPatterns.filter { it.name == pattern }
                .map { ModuleConfigurationPattern(it.name, configPattern.description, configPattern.defaultValue) }
                .first()

        moduleRepository.save(module)
    }

    private fun to(configPatterns: List<ConfigurationPatternDTO>): List<ModuleConfigurationPattern> {
       return configPatterns.map {
            pattern -> ModuleConfigurationPattern(pattern.name, pattern.description, pattern.defaultValue) }
    }

}