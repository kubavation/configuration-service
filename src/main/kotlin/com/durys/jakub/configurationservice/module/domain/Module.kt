package com.durys.jakub.configurationservice.module.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("module")
internal class Module(@Id val id: String, val name: String, val description: String,
                      var configPatterns: List<ModuleConfigurationPattern> = emptyList(),
                      var configGroups: List<ModuleConfigurationGroup> = emptyList()) {
    constructor(name: String, description: String, configPatterns: List<ModuleConfigurationPattern>, configGroups: List<ModuleConfigurationGroup>)
            : this(UUID.randomUUID().toString(), name, description, configPatterns, configGroups)

    infix fun with(patterns: List<ModuleConfigurationPattern>): Module {
        this.configPatterns = this.configPatterns + patterns
        return this
    }

    infix fun with(groups: List<ModuleConfigurationGroup>): Module {
        this.configGroups = this.configGroups + groups
        return this
    }
}