package com.durys.jakub.configurationservice.moduleconfiguration.infrastructure

import com.durys.jakub.configurationservice.moduleconfiguration.domain.ModuleConfiguration
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

internal interface ModuleConfigurationRepository: MongoRepository<ModuleConfiguration, String> {

    @Query("{'context': ?0, 'module': ?1}")
    fun moduleConfiguration(context: String, moduleName: String): ModuleConfiguration?

    @Query("{'module': ?0}")
    fun moduleConfigurations(moduleName: String): List<ModuleConfiguration>
}