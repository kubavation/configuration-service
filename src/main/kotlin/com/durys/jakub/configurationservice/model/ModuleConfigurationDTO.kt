package com.durys.jakub.configurationservice.model

data class ModuleConfigurationDTO(val moduleName: String, val configuration: List<Configuration> = emptyList())