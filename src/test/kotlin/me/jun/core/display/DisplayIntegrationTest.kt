package me.jun.core.display

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.restassured.RestAssured.given
import me.jun.core.blog.application.dto.CreateArticleRequest
import me.jun.support.IntegrationTest
import me.jun.support.createArticleRequest
import org.hamcrest.Matchers.hasKey
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class DisplayIntegrationTest: IntegrationTest() {

    @Test
    fun displayTest() {
        register()
        login()

        for (count in 1..20) {
            createArticle()
        }

        retrieveDisplay()
    }

    private fun createArticle(category: String = "DefaultCategoryName") {
        val content: CreateArticleRequest = createArticleRequest().apply {
            this.categoryName = category
        }

        val response = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .body(content)

            .`when`()
            .post("/api/blog/articles")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("id") }
            .assertThat().body("$") { hasKey("title") }
            .assertThat().body("$") { hasKey("content") }
            .assertThat().body("$") { hasKey("writerId") }
            .assertThat().body("$") { hasKey("createdAt") }
            .assertThat().body("$") { hasKey("updatedAt") }
            .extract()
            .asString()

        val element: JsonElement = JsonParser.parseString(response)
        println(gson.toJson(element))
    }

    private fun retrieveDisplay(): Unit {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .queryParam("page", 1)
            .queryParam("size", 10)

            .`when`()
            .get("/api/display")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("page") }
            .assertThat().body("page") { hasKey("size") }
            .extract()
            .asString()

        val element: JsonElement = JsonParser.parseString(response)
        println(gson.toJson(element))
    }
}