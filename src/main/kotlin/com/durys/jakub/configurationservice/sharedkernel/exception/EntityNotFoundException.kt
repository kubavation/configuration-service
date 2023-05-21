package com.durys.jakub.configurationservice.sharedkernel.exception

class EntityNotFoundException(private val id: String): RuntimeException("Entity of id [$id] not found")