package me.jun.core.blog.persentation

import com.fasterxml.jackson.databind.ObjectMapper
import me.jun.common.security.JwtProvider
import me.jun.common.security.MemberIdExtractor
import me.jun.common.security.exception.InvalidTokenException
import me.jun.core.blog.application.ArticleService
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.support.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var articleService: ArticleService

    @MockBean
    private lateinit var jwtProvider: JwtProvider

    @MockBean
    private lateinit var memberIdExtractor: MemberIdExtractor

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun createArticleTest() {
        val content: String = objectMapper.writeValueAsString(createArticleRequest().apply { this.writerId = null })

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        given(articleService.createArticle(any()))
            .willReturn(articleResponse())

        mockMvc.perform(
            post("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("title").exists())
            .andExpect(jsonPath("content").exists())
            .andExpect(jsonPath("writerId").exists())
            .andExpect(jsonPath("createdAt").exists())
            .andExpect(jsonPath("updatedAt").exists())
    }

    @Test
    fun noToken_createArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(createArticleRequest())

        mockMvc.perform(
            post("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun invalidToken_createArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(createArticleRequest())

        given(jwtProvider.extractSubject(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            post("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun noContent_createArticleFailTest() {
        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(WRITER_ID)

        mockMvc.perform(
            post("/api/blog/articles")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun unknownWriter_createArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(createArticleRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            post("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun retrieveArticleTest() {
        given(articleService.retrieveArticle(any()))
            .willReturn(articleResponse())

        mockMvc.perform(
            get("/api/blog/articles/1")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("title").exists())
            .andExpect(jsonPath("content").exists())
            .andExpect(jsonPath("writerId").exists())
            .andExpect(jsonPath("createdAt").exists())
            .andExpect(jsonPath("updatedAt").exists())
    }

    @Test
    fun wrongPathVariable_retrieveArticleFailTest() {
        mockMvc.perform(
            get("/api/blog/articles/asdf")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists());
    }

    @Test
    fun noArticle_retrieveArticleFailTest() {
        given(articleService.retrieveArticle(any()))
            .willThrow(ArticleNotFoundException.of("1"))

        mockMvc.perform(
            get("/api/blog/articles/1")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun updateArticleTest() {
        val content: String = objectMapper.writeValueAsString(updateArticleRequest().apply { this.writerId = null })

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(WRITER_ID)

        given(articleService.updateArticle(any()))
            .willReturn(updatedArticleResponse())

        mockMvc.perform(
            put("/api/blog/articles")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("title").exists())
            .andExpect(jsonPath("content").exists())
            .andExpect(jsonPath("writerId").exists())
            .andExpect(jsonPath("createdAt").exists())
            .andExpect(jsonPath("updatedAt").exists())
    }

    @Test
    fun noToken_updateArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(updateArticleRequest())

        mockMvc.perform(
            put("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun invalidToken_updateArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(updateArticleRequest())

        given(jwtProvider.extractSubject(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            put("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun noContent_updateArticleFailTest() {
        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(WRITER_ID)

        mockMvc.perform(
            put("/api/blog/articles")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun unknownWriter_updateArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(updateArticleRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            put("/api/blog/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }
}