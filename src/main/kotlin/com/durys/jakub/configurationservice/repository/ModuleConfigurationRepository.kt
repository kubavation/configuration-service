package com.durys.jakub.configurationservice.repository

import com.durys.jakub.configurationservice.model.ModuleConfiguration
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ModuleConfigurationRepository: MongoRepository<ModuleConfiguration, String> {

    @Query("{'name': ?0}")
    fun moduleConfiguration(moduleName: String): ModuleConfiguration?
}