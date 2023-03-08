package com.durys.jakub.configurationservice.service

import com.durys.jakub.configurationservice.model.ModuleConfigurationDTO
import com.durys.jakub.configurationservice.repository.ModuleConfigurationRepository
import org.springframework.stereotype.Service

@Service
internal class ModuleConfigurationService(val repository: ModuleConfigurationRepository) {

    fun availableModules() = repository.availableModules()

    fun moduleConfiguration(moduleName: String): ModuleConfigurationDTO {
       return repository.moduleConfiguration(moduleName) ?: throw RuntimeException("Configuration for module $moduleName not found");
    }

    fun setModuleConfiguration(moduleName: String, config: ModuleConfigurationDTO) {
       repository.findByName(moduleName)?.let {
           repository.save(it.copy(configurations = config.configuration)) }
               ?: throw RuntimeException("Module $moduleName not found")
    }

    fun isConfigEnabled(moduleName: String, configName: String): Boolean {
        return repository.moduleConfiguration(moduleName)?.let {
            it.configuration.stream()
                .filter { c -> c.name == configName }
                .map { c -> c.value }
                .findFirst()
                .orElse(false)
        } ?: false
    }
}