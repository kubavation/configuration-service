package com.durys.jakub.configurationservice.events

import java.time.LocalDateTime
import java.util.UUID

open class DomainEvent(val id: UUID, val at: LocalDateTime) {
}