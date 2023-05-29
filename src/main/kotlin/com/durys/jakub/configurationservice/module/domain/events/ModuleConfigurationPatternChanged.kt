package com.durys.jakub.configurationservice.module.domain.events

import com.durys.jakub.configurationservice.events.DomainEvent
import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import java.time.LocalDateTime
import java.util.UUID

internal class ModuleConfigurationPatternChanged(id: UUID, at: LocalDateTime,
                                                 val module: String, val patterns: ModuleConfigurationPattern): DomainEvent(id, at)