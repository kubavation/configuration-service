package com.durys.jakub.configurationservice.module.domain

import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import org.springframework.stereotype.Service

@Service
internal class ModuleValidationService(val moduleRepository: ModuleRepository) {

    fun moduleWithNameExists(module: String) = moduleRepository.findByName(module).isPresent

}