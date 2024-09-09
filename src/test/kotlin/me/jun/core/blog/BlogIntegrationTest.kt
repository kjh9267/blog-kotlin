package me.jun.core.blog

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.restassured.RestAssured.given
import me.jun.support.IntegrationTest
import me.jun.support.createArticleRequest
import me.jun.support.updateArticleRequest
import org.hamcrest.Matchers.hasKey
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class BlogIntegrationTest: IntegrationTest() {

    @Test
    fun blogTest() {
        register()
        login()
        createArticle()
        retrieveArticle(1L)
        updateArticle()
        deleteArticle(1L)
    }

    @Test
    fun retrievePagedArticlesTest() {
        register()
        login()

        for (count in 1..10) {
            createArticle()
        }

        retrievePagedArticles(0, 10)
    }

    private fun createArticle() {
        val response = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .body(createArticleRequest())

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

    private fun retrieveArticle(id: Long) {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)

            .`when`()
            .get("/api/blog/articles/$id")

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

    private fun updateArticle() {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .body(updateArticleRequest())

            .`when`()
            .put("/api/blog/articles")

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

    private fun deleteArticle(id: Long) {
        val response: String = given()
            .log().all()
            .port(port!!)
            .header(AUTHORIZATION, token)

            .`when`()
            .delete("/api/blog/articles/$id")

            .then()
            .statusCode(NO_CONTENT.value())
            .extract()
            .asString()

        val element: JsonElement = JsonParser.parseString(response)
        println(gson.toJson(element))
    }

    private fun retrievePagedArticles(page: Int, size: Int): Unit {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)

            .`when`()
            .get("/api/blog/articles/query?page=$page&size=$size")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("articleResponses") }
            .assertThat().body("articleResponses") { hasKey("size") }
            .extract()
            .asString()

        val element: JsonElement = JsonParser.parseString(response)
        println(gson.toJson(element))
    }
}