package com.durys.jakub.configurationservice.events

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
}