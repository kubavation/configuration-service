package com.durys.jakub.configurationservice.controller

import com.durys.jakub.configurationservice.model.ContextDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/contexts")
@RestController
internal class ContextController {

    @GetMapping
    fun getAvailableContexts(): List<ContextDTO> = listOf(ContextDTO("Warsaw"), ContextDTO("Krakow"))
}