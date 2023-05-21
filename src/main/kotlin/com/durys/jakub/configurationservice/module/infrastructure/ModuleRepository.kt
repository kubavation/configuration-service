package com.durys.jakub.configurationservice.module.infrastructure

import com.durys.jakub.configurationservice.module.domain.Module
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
internal interface ModuleRepository: MongoRepository<Module, String>