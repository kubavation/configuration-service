package com.durys.jakub.configurationservice.moduleconfiguration.application

import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model.ModuleConfigurationDTO
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import org.springframework.stereotype.Service

@Service
internal class ModuleConfigurationService(val moduleRepository: ModuleRepository,
                                          val moduleConfigurationRepository: ModuleConfigurationRepository) {

    fun moduleConfiguration(context: String, moduleName: String): ModuleConfigurationDTO {
       return moduleConfigurationRepository.moduleConfiguration(context, moduleName)
               ?: throw RuntimeException("Configuration for module $moduleName not found");
    }

    fun setModuleConfiguration(context: String, moduleName: String, config: ModuleConfigurationDTO) {
       moduleConfigurationRepository.findByModule(moduleName)?.let {
           moduleConfigurationRepository.save(it.copy(configurations = config.configuration)) }
               ?: throw RuntimeException("Module $moduleName not found")
    }

    fun isConfigEnabled(context: String, moduleName: String, configName: String): Boolean {
        return moduleConfigurationRepository.moduleConfiguration(context, moduleName)?.let {
            it.configuration.stream()
                .filter { c -> c.name == configName }
                .map { c -> c.value }
                .findFirst()
                .orElse(false)
        } ?: false
    }
}