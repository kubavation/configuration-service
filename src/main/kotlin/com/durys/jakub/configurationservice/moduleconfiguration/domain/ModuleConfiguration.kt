package com.durys.jakub.configurationservice.moduleconfiguration.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("module_configurations")
internal data class ModuleConfiguration(@Id val id: String, val context: String, val module: String,
                               val configurations: List<Configuration> = emptyList())