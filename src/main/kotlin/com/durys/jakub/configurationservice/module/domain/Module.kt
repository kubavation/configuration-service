package com.durys.jakub.configurationservice.module.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("module")
internal class Module(@Id val id: String, val name: String, val description: String,
                      var configPatterns: List<ModuleConfigurationPattern> = listOf()) {
    constructor(name: String, description: String, configPatterns: List<ModuleConfigurationPattern>)
            : this(UUID.randomUUID().toString(), name, description, configPatterns)
}