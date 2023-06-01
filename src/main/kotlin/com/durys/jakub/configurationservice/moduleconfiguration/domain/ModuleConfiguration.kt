package com.durys.jakub.configurationservice.moduleconfiguration.domain

import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("module_configuration")
internal data class ModuleConfiguration(@Id val id: String, val context: String, val module: String,
                               var configurations: List<Configuration> = emptyList()) {

    constructor(context: String, module: String, configurations: List<Configuration>)
            : this(UUID.randomUUID().toString(), context, module, configurations)


    fun configEnabled(config: String): Boolean {
        return configurations.find { it.name == config }?.value ?: false;
    }

    fun updateConfigurations(patterns: List<ModuleConfigurationPattern>): ModuleConfiguration {
        configurations = patterns.map { updateConfiguration(it) }.filterNotNull()
        return this
    }

    private fun updateConfiguration(pattern: ModuleConfigurationPattern): Configuration? {
        return if (!hasConfiguration(pattern.name)) {
            Configuration(pattern.name, pattern.description, pattern.defaultValue)
        } else {
            configurations.find { it.name == pattern.name }?.let { Configuration(it.name, pattern.description, it.value) }
        }
    }

    private fun hasConfiguration(configName: String) = configurations.find { it.name === configName } != null

}