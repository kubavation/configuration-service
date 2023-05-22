package com.durys.jakub.configurationservice.module.infrastructure

import com.durys.jakub.configurationservice.module.domain.Module
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface ModuleRepository: MongoRepository<Module, String> {
    fun findByName(name: String): Optional<Module>
}