package com.durys.jakub.configurationservice.moduleconfiguration.application

import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.ModuleConfigurationRepository
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model.ModuleConfigurationDTO
import org.springframework.stereotype.Service

@Service
internal class ModuleConfigurationService(val moduleConfigurationRepository: ModuleConfigurationRepository) {

    fun moduleConfiguration(context: String, moduleName: String): ModuleConfigurationDTO {
       return moduleConfigurationRepository.moduleConfiguration(context, moduleName)?.let {
           ModuleConfigurationDTO(it.configurations)
       } ?: throw RuntimeException("Configuration for module $moduleName not found")
    }

    fun setModuleConfiguration(context: String, moduleName: String, config: ModuleConfigurationDTO) {
       moduleConfigurationRepository.findByModule(moduleName)?.let {
           moduleConfigurationRepository.save(it.copy(configurations = config.configuration)) }
               ?: throw RuntimeException("Module $moduleName not found")
    }

    fun isConfigEnabled(context: String, moduleName: String, configName: String): Boolean {
        return moduleConfigurationRepository.moduleConfiguration(context, moduleName)?.configEnabled(configName) ?: false
    }
}   