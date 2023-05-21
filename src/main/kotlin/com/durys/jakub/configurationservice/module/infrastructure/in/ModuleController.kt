package com.durys.jakub.configurationservice.module.infrastructure.`in`

import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/modules")
internal class ModuleController(val moduleRepository: ModuleRepository) {
}