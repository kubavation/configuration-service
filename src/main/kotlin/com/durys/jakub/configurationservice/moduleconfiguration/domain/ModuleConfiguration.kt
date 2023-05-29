package com.durys.jakub.configurationservice.moduleconfiguration.domain

import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("module_configuration")
internal data class ModuleConfiguration(@Id val id: String, val context: String, val module: String,
                               var configurations: List<Configuration> = emptyList()) {


    fun configEnabled(config: String): Boolean {
        return configurations.find { it.name == config }?.value ?: false;
    }

    fun updateConfigurations(patterns: List<ModuleConfigurationPattern>): ModuleConfiguration {
        configurations = patterns.flatMap{ updateConfiguration(it) }
        return this
    }

    private fun updateConfiguration(pattern: ModuleConfigurationPattern): List<Configuration> {
        return if (!hasConfiguration(pattern.name)) {
            configurations + Configuration(pattern.name, pattern.description, pattern.defaultValue)
        } else {
            configurations.filter { it.name != pattern.name } + configurations.filter { it.name == pattern.name }
                    .map { Configuration(it.name, pattern.description, it.value) }
        }
    }

    private fun hasConfiguration(configName: String) = configurations.find { it.name === configName } != null

}