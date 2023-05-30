package com.durys.jakub.configurationservice.context.infrastructure

import com.durys.jakub.configurationservice.context.domain.Context
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface ContextRepository: MongoRepository<Context, String> {
    fun findByName(name: String): Optional<Context>
}