package com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model

import com.durys.jakub.configurationservice.moduleconfiguration.domain.Configuration

data class ModuleConfigurationDTO(val configuration: List<Configuration> = emptyList())