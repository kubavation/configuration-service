package com.durys.jakub.configurationservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("module_configurations")
data class ModuleConfiguration(@Id val name: String, val desc: String, val configurations: List<Configuration> = emptyList())