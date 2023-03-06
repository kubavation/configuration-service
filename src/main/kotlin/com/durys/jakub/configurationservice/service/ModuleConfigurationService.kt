package com.durys.jakub.configurationservice.service

import com.durys.jakub.configurationservice.model.ModuleConfiguration
import com.durys.jakub.configurationservice.repository.ModuleConfigurationRepository
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class ModuleConfigurationService(val repository: ModuleConfigurationRepository) {

    fun availableModules() = repository.availableModules()

    fun moduleConfiguration(moduleName: String): ModuleConfiguration {
       return repository.moduleConfiguration(moduleName) ?: throw RuntimeException("Configuration for module $moduleName not found");
    }

    fun saveModuleConfiguration(moduleConfig: ModuleConfiguration) {
        repository.save(moduleConfig)
    }
}