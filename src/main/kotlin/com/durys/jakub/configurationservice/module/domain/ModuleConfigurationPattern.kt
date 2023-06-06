package com.durys.jakub.configurationservice.module.domain

internal class ModuleConfigurationPattern(val name: String, val description: String,
                                          val defaultValue: Boolean = false, val group: ModuleConfigurationGroup? = null)