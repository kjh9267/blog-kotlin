package me.jun.core.blog

import me.jun.support.IntegrationTest
import org.junit.jupiter.api.Test

class BlogIntegrationTest: IntegrationTest() {

    @Test
    fun blogTest() {
        register()
        token = login()
    }
}