package com.durys.jakub.configurationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConfigurationServiceApplication

fun main(args: Array<String>) {
	runApplication<ConfigurationServiceApplication>(*args)
}
