package com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.`in`

import com.durys.jakub.configurationservice.moduleconfiguration.application.ModuleConfigurationService
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model.ModuleConfigurationDTO
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*

@RequestMapping("/module-configuration")
@RestController
internal class ModuleConfigurationController(val moduleConfigurationService: ModuleConfigurationService) {

    @Cacheable(value = ["config"], key = "#context.#moduleName")
    @GetMapping("/{context}/{moduleName}")
    fun getModuleConfiguration(@PathVariable context: String, @PathVariable moduleName: String): ModuleConfigurationDTO {
        return moduleConfigurationService.moduleConfiguration(context, moduleName)
    }

    @CachePut(value = ["config"], key = "#context.#moduleName")
    @PatchMapping("/{context}/{moduleName}")
    fun setModuleConfiguration(@PathVariable context: String, @PathVariable moduleName: String, @RequestBody config: ModuleConfigurationDTO) {
        moduleConfigurationService.setModuleConfiguration(context, moduleName, config)
    }
}