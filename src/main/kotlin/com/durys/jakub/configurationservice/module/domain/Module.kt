package com.durys.jakub.configurationservice.module.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("module")
internal class Module(@Id val name: String, val description: String, val configPatterns: List<ModuleConfigurationPattern> = listOf())