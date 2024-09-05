package me.jun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BlogKotlinApplication

fun main(args: Array<String>) {
    runApplication<BlogKotlinApplication>(*args)
}
