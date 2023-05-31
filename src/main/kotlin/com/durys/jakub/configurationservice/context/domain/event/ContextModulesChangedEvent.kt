package com.durys.jakub.configurationservice.context.domain.event

import com.durys.jakub.configurationservice.events.DomainEvent
import java.time.LocalDateTime
import java.util.UUID

class ContextModulesChangedEvent(val context: String, val moduleNames: List<String>): DomainEvent(UUID.randomUUID(), LocalDateTime.now())