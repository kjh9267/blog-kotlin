package me.jun.core.guestbook

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.restassured.RestAssured.given
import me.jun.support.IntegrationTest
import me.jun.support.createPostRequest
import me.jun.support.updatePostRequest
import org.hamcrest.Matchers.hasKey
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class GuestbookIntegrationTest: IntegrationTest() {

    @Test
    fun guestbookTest() {
        register()
        login()
        createPost()
        retrievePost(1L)
        updatePost()
        deletePost(1L)
    }

    private fun createPost() {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .body(createPostRequest())

            .`when`()
            .post("/api/guestbook/posts")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("postId") }
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
    
    private fun retrievePost(id: Long) {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)

            .`when`()
            .get("/api/guestbook/posts/$id")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("postId") }
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

    private fun updatePost() {
        val response: String = given()
            .log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .body(updatePostRequest())

            .`when`()
            .put("/api/guestbook/posts")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("postId") }
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

    private fun deletePost(id: Long) {
        val response: String = given()
            .log().all()
            .port(port!!)
            .header(AUTHORIZATION, token)

            .`when`()
            .delete("/api/guestbook/posts/$id")

            .then()
            .statusCode(NO_CONTENT.value())
            .extract()
            .asString()

        val element: JsonElement = JsonParser.parseString(response)
        println(gson.toJson(element))
    }
}