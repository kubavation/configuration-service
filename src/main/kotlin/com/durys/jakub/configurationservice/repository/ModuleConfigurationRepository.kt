package com.durys.jakub.configurationservice.repository

import org.springframework.data.mongodb.repository.MongoRepository

interface ModuleConfigurationRepository: MongoRepository<Module, String> {
}