package com.durys.jakub.configurationservice.module.infrastructure

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
internal interface ModuleRepository: MongoRepository<Module, String>