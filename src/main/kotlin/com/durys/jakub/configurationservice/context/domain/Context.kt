package com.durys.jakub.configurationservice.context.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("context")
internal data class Context(@Id val id: String, val name: String, val modules: List<ContextModule>)  {

    constructor(name: String, modules: List<ContextModule>): this(UUID.randomUUID().toString(), name, modules)

}