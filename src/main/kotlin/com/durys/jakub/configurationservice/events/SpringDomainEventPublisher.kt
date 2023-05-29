package com.durys.jakub.configurationservice.events

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringDomainEventPublisher(val applicationEventPublisher: ApplicationEventPublisher): DomainEventPublisher {

    override fun publish(event: DomainEvent) = applicationEventPublisher.publishEvent(event)
}