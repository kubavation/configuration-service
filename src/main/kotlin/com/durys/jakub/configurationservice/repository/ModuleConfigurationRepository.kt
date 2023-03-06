package com.durys.jakub.configurationservice.repository

import com.durys.jakub.configurationservice.model.ModuleConfiguration
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ModuleConfigurationRepository: MongoRepository<ModuleConfiguration, String> {

    @Query("{'name': ?0, 'active': true}")
    fun moduleConfiguration(moduleName: String): ModuleConfiguration?

    @Query(value = "{'active': true}", fields = "{configurations: 0}")
    fun availableModules(): List<ModuleConfiguration>
}