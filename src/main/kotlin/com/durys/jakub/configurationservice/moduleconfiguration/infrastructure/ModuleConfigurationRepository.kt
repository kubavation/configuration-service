package com.durys.jakub.configurationservice.moduleconfiguration.infrastructure

import com.durys.jakub.configurationservice.moduleconfiguration.domain.ModuleConfiguration
import com.durys.jakub.configurationservice.moduleconfiguration.infrastructure.model.ModuleConfigurationDTO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

internal interface ModuleConfigurationRepository: MongoRepository<ModuleConfiguration, String> {

    fun findByName(moduleName: String): ModuleConfiguration?

    @Query("{'name': ?0, 'active': true}")
    fun moduleConfiguration(moduleName: String): ModuleConfigurationDTO?
}