package com.durys.jakub.configurationservice.context.domain.exception

class ContextAlreadyExistsException(private val name: String): RuntimeException("Context with name: $name already exists")