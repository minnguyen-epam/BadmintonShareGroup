package com.app.badmintonsharegroup

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<BadmintonShareGroupApplication>().with(TestcontainersConfiguration::class).run(*args)
}
