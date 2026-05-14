package com.app.badmintonsharegroup

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BadmintonShareGroupApplication

fun main(args: Array<String>) {
    runApplication<BadmintonShareGroupApplication>(*args)
}
