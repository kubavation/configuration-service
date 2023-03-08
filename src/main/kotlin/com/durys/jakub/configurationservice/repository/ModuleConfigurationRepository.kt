package com.durys.jakub.configurationservice.repository

import com.durys.jakub.configurationservice.model.ModuleConfiguration
import com.durys.jakub.configurationservice.model.ModuleConfigurationDTO
import com.durys.jakub.configurationservice.model.ModuleDTO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

internal interface ModuleConfigurationRepository: MongoRepository<ModuleConfiguration, String> {

    fun findByName(moduleName: String): ModuleConfiguration?

    @Query("{'name': ?0, 'active': true}")
    fun moduleConfiguration(moduleName: String): ModuleConfigurationDTO?

    @Query(value = "{'active': true}")
    fun availableModules(): List<ModuleDTO>
}