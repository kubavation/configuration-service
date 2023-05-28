package com.durys.jakub.configurationservice.api

import com.durys.jakub.configurationservice.moduleconfiguration.application.ModuleConfigurationService
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model.ModuleConfigurationDTO
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/modules")
@RestController
internal class ModuleConfigurationApi(val moduleConfigurationService: ModuleConfigurationService) {

    @Cacheable(value = ["config"], key = "#context.#moduleName")
    @GetMapping("/{context}/{moduleName}/configuration")
    fun getModuleConfiguration(@PathVariable context: String, @PathVariable moduleName: String): ModuleConfigurationDTO {
        return moduleConfigurationService.moduleConfiguration(context, moduleName)
    }

    @GetMapping("/{context}/{moduleName}/configuration/{configName}")
    fun isConfigEnabled(@PathVariable context: String, @PathVariable moduleName: String, @PathVariable configName: String): Boolean {
        return moduleConfigurationService.isConfigEnabled(context, moduleName, configName)
    }
}