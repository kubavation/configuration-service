package com.durys.jakub.configurationservice.module.domain.events

import com.durys.jakub.configurationservice.events.DomainEvent
import com.durys.jakub.configurationservice.module.domain.ModuleConfigurationPattern
import java.time.LocalDateTime
import java.util.*

internal class ModuleConfigurationPatternChanged(val module: String, val patterns: List<ModuleConfigurationPattern>)
    : DomainEvent(UUID.randomUUID(), LocalDateTime.now())