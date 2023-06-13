package com.durys.jakub.configurationservice.module.infrastructure.`in`

import com.durys.jakub.configurationservice.module.domain.Module
import com.durys.jakub.configurationservice.module.domain.ModuleValidationService
import com.durys.jakub.configurationservice.module.infrastructure.ModuleRepository
import com.durys.jakub.configurationservice.module.infrastructure.model.ModuleDTO
import com.durys.jakub.configurationservice.sharedkernel.exception.EntityNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/modules")
internal class ModuleController(val moduleRepository: ModuleRepository,
                                val moduleValidationService: ModuleValidationService) {

    @GetMapping
    fun getModules(): List<ModuleDTO> = moduleRepository.findAll().map { ModuleDTO(it.name, it.description) }

    @GetMapping("{moduleName}/validation/name-exists")
    fun moduleAlreadyExists(@PathVariable moduleName: String) = moduleValidationService.moduleWithNameExists(moduleName)

    @PostMapping
    fun addModule(@RequestBody moduleDTO: ModuleDTO) = moduleRepository.save(
            Module(moduleDTO.name, moduleDTO.description, emptyList(), emptyList()))

    @PatchMapping("/{name}")
    fun editModule(@PathVariable name: String, @RequestBody moduleDTO: ModuleDTO) {

        val module = moduleRepository.findByName(name)
                .map { Module(it.id, moduleDTO.name, moduleDTO.description, it.configPatterns, it.configGroups) }
                .orElseThrow { EntityNotFoundException(name) }

        moduleRepository.save(module)
    }

    @DeleteMapping("/{name}")
    fun deleteModule(@PathVariable name: String) {
        moduleRepository.findByName(name)
                .ifPresentOrElse({module ->  moduleRepository.delete(module) }, {throw EntityNotFoundException(name) })
    }



}