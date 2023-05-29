package com.durys.jakub.configurationservice.moduleconfiguration.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("module_configuration")
internal data class ModuleConfiguration(@Id val id: String, val context: String, val module: String,
                               var configurations: List<Configuration> = emptyList()) {


    fun configEnabled(config: String): Boolean {
        return configurations.find { it.name == config }?.value ?: false;
    }

    fun addConfigurationIfNotExists(configuration: Configuration) {
        if (!hasConfiguration(configuration.name)) {
            configurations = configurations + configuration
        }
    }

    private fun hasConfiguration(configName: String) = configurations.find { it.name === configName } != null

}