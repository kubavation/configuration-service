package com.durys.jakub.configurationservice.module.infrastructure.`in`

import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationGroup
import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.module.infrastructure.model.ConfigurationGroupDTO
import com.durys.jakub.configurationservice.module.infrastructure.model.ConfigurationPatternDTO
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/modules/{moduleName}/configuration-groups")
internal class ModuleConfigurationGroupsController(val moduleRepository: ModuleRepository) {

    @GetMapping
    fun getModuleConfigurationGroups(@PathVariable moduleName: String): List<ConfigurationGroupDTO> {
        return moduleRepository.findByName(moduleName)
                .map { it.configGroups.map {group -> ConfigurationGroupDTO(group.name, group.description) } }
                .orElseThrow { EntityNotFoundException(moduleName) }
    }

    @GetMapping("/{group}/patterns")
    fun getModuleConfigurationGroupPatterns(@PathVariable moduleName: String, @PathVariable group: String): List<ConfigurationPatternDTO> {
        val patterns = moduleRepository.findByName(moduleName)
                .map { it.configPatterns }
                .orElseThrow { EntityNotFoundException(moduleName) }

        return patterns.filter { pattern -> pattern.group?.name == group }
                .map { ConfigurationPatternDTO(it.name, it.description, it.defaultValue) }
    }

    @PatchMapping
    fun addModuleConfigurationGroup(@PathVariable moduleName: String, @RequestBody configGroup: ConfigurationGroupDTO) {
        val module = moduleRepository.findByName(moduleName)
                .map { it withGroups listOf(ModuleConfigurationGroup(configGroup.name, configGroup.description)) }
                .orElseThrow { EntityNotFoundException(moduleName) }
        moduleRepository.save(module)
    }

    @DeleteMapping("/{group}")
    fun deleteModuleConfigurationGroup(@PathVariable moduleName: String, @PathVariable group: String) {

        val module = moduleRepository.findByName(moduleName)
                .map { it.configGroups = it.configGroups.filter {configGroup -> configGroup.name != group }; return@map it }
                .orElseThrow { EntityNotFoundException(moduleName) }

        moduleRepository.save(module)
    }

    @PutMapping("/{group}")
    fun editModuleConfigurationGroup(@PathVariable moduleName: String, @PathVariable group: String,
                                     @RequestBody configGroup: ConfigurationGroupDTO) {

        val module = moduleRepository.findByName(moduleName)
                .map {module -> module.configGroups.filter { it.name != group } + module.configGroups.filter { it.name == group }
                        .map { ModuleConfigurationGroup(it.name, configGroup.description) }
                        .first()
                    return@map module
                }
                .orElseThrow { EntityNotFoundException(moduleName) }

        moduleRepository.save(module)
    }

}