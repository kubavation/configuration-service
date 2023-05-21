package com.durys.jakub.configurationservice.moduleconfiguration.domain

import com.durys.jakub.configurationservice.moduleconfiguration.domain.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("module_configurations")
internal data class ModuleConfiguration(@Id val id: String, val name: String, val desc: String, val active: Boolean = true,
                               val configurations: List<Configuration> = emptyList())