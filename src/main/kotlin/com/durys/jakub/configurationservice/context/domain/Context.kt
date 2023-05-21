package com.durys.jakub.configurationservice.context.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("context")
internal data class Context(@Id val name: String, var modules: List<ContextModule>)  {
}