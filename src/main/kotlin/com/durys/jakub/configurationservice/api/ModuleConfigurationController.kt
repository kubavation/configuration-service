package com.durys.jakub.configurationservice.api

import com.durys.jakub.configurationservice.model.ModuleConfiguration
import com.durys.jakub.configurationservice.service.ModuleConfigurationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class ModuleConfigurationController(val moduleConfigurationService: ModuleConfigurationService) {

    @GetMapping("/modules/{moduleName}")
    fun getModuleConfiguration(@PathVariable moduleName: String): ModuleConfiguration {
        return moduleConfigurationService.moduleConfiguration(moduleName);
    }
}