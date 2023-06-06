package com.durys.jakub.configurationservice.module.infrastructure.`in`

import com.durys.jakub.configurationservice.events.DomainEventPublisher
import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import com.durys.jakub.configurationservice.module.domain.events.ModuleConfigurationPatternChanged
import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.module.infrastructure.model.ConfigurationPatternDTO
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/modules/{moduleName}/configuration-pattern")
internal class ModuleConfigurationPatternController(val moduleRepository: ModuleRepository, val domainEventPublisher: DomainEventPublisher) {

    @GetMapping
    fun getModuleConfigurationPattern(@PathVariable moduleName: String): List<ConfigurationPatternDTO> {
        return moduleRepository.findByName(moduleName)
                .map { it.configPatterns.map {pattern -> ConfigurationPatternDTO(pattern.name, pattern.description, pattern.defaultValue) } }
                .orElseThrow { EntityNotFoundException(moduleName) }
    }

    @PatchMapping
    fun setModuleConfigurationPatterns(@PathVariable moduleName: String, @RequestBody configPatterns: List<ConfigurationPatternDTO>) {
        val module = moduleRepository.findByName(moduleName)
                .map { it with asConfigPatterns(configPatterns) }
                .map { moduleRepository.save(it) }
                .orElseThrow { EntityNotFoundException(moduleName) }

        domainEventPublisher.publish(ModuleConfigurationPatternChanged(module.name, module.configPatterns))
    }

    @DeleteMapping("/{pattern}")
    fun deleteModuleConfigurationPattern(@PathVariable moduleName: String, @PathVariable pattern: String) {

        val module = moduleRepository.findByName(moduleName)
                .map { it.configPatterns = it.configPatterns.filter {configPattern -> configPattern.name != pattern }; return@map it }
                .map { moduleRepository.save(it)}
                .orElseThrow { EntityNotFoundException(moduleName) }

        domainEventPublisher.publish(ModuleConfigurationPatternChanged(module.name, module.configPatterns))
    }

    @PatchMapping("/{pattern}")
    fun patchModuleConfigurationPattern(@PathVariable moduleName: String, @PathVariable pattern: String,
                                        @RequestBody configPattern: ConfigurationPatternDTO) {

        val module = moduleRepository.findByName(moduleName)
                .map {module -> module.configPatterns.filter { it.name != pattern } + module.configPatterns.filter { it.name == pattern }
                        .map { ModuleConfigurationPattern(it.name, configPattern.description, configPattern.defaultValue) }
                        .first()
                    return@map module
                }
                .map { moduleRepository.save(it) }
                .orElseThrow { EntityNotFoundException(moduleName) }

        domainEventPublisher.publish(ModuleConfigurationPatternChanged(module.name, module.configPatterns))
    }


    private fun asConfigPatterns(configPatterns: List<ConfigurationPatternDTO>): List<ModuleConfigurationPattern> {
        return configPatterns.map {
            pattern -> ModuleConfigurationPattern(pattern.name, pattern.description, pattern.defaultValue) }
    }

}