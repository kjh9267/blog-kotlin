package me.jun.support

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.hasKey
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class IntegrationTest {

    @LocalServerPort
    protected var port: Int? = null

    protected lateinit var token: String

    protected val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    fun register(): Unit {
        val response: String = given().log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .body(registerRequest())

            .`when`()
            .post("/api/member/register")

            .then()
            .statusCode(OK.value())
            .extract()
            .asString()

        val element: JsonElement = JsonParser.parseString(response)
        println(gson.toJson(element))
    }

    fun login(): Unit {
        token = given().log().all()
            .port(port!!)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE)
            .body(loginRequest())

            .`when`()
            .post("/api/member/login")

            .then()
            .statusCode(OK.value())
            .assertThat().body("$") { hasKey("token") }
            .extract()
            .path("token")

        println(token)
    }
}