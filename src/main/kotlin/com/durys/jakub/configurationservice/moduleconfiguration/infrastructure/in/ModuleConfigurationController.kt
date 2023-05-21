package com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.`in`

import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model.ModuleConfigurationDTO
import com.durys.jakub.configurationservice.moduleconfiguration.application.ModuleConfigurationService
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/module-configuration")
@RestController
internal class ModuleConfigurationController(val moduleConfigurationService: ModuleConfigurationService) {

    @Cacheable(value = ["config"], key = "#moduleName")
    @GetMapping("/{moduleName}")
    fun getModuleConfiguration(@PathVariable moduleName: String): ModuleConfigurationDTO {
        return moduleConfigurationService.moduleConfiguration(moduleName)
    }

    @CachePut(value = ["config"], key = "#moduleName")
    @PostMapping("/{moduleName}")
    fun setModuleConfiguration(@PathVariable moduleName: String, @RequestBody config: ModuleConfigurationDTO) {
        moduleConfigurationService.setModuleConfiguration(moduleName, config)
    }
}