package com.durys.jakub.configurationservice.model

data class Module(val name: String, val desc: String, val configurations: List<Configuration> = emptyList())