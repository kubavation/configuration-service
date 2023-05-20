package com.durys.jakub.configurationservice.context.infrastructure

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/contexts")
@RestController
internal class ContextController {

    @GetMapping
    fun getAvailableContexts(): List<ContextDTO> = listOf(ContextDTO("Warsaw"), ContextDTO("Krakow"))
}