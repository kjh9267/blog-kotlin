package me.jun.core.blog.persentation

import com.fasterxml.jackson.databind.ObjectMapper
import me.jun.common.security.JwtProvider
import me.jun.common.security.MemberIdExtractor
import me.jun.common.security.exception.InvalidTokenException
import me.jun.core.blog.application.TaggedArticleService
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.support.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var taggedArticleService: TaggedArticleService

    @MockBean
    private lateinit var jwtProvider: JwtProvider

    @MockBean
    private lateinit var memberIdExtractor: MemberIdExtractor

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun addTagToArticleTest() {
        val content: String = objectMapper.writeValueAsString(addTagRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        given(taggedArticleService.addTagToArticle(any()))
            .willReturn(taggedArticleResponse())

        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("taggedArticleId").exists())
            .andExpect(jsonPath("tagId").exists())
            .andExpect(jsonPath("articleId").exists())
    }

    @Test
    fun noArticle_addTagToArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(addTagRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        given(taggedArticleService.addTagToArticle(any()))
            .willThrow(ArticleNotFoundException.of(ARTICLE_ID.toString()))

        mockMvc.perform(
            post("/api/blog/tags")
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
    fun noContent_addTagToArticleFailTest() {
        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun wrongContent_addTagToArticleFailTest() {
        val wrongContent: String = objectMapper.writeValueAsString(
            addTagRequest().apply {
                this.articleId = -1
                this.tagName = ""
            }
        )

        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(wrongContent)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun noToken_addTagToArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(addTagRequest())

        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun wrongToken_addTagToArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(addTagRequest())

        given(jwtProvider.extractSubject(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, "wrong token")
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun unknownWriter_addTagToArticleFailTest() {
        val content: String = objectMapper.writeValueAsString(addTagRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            post("/api/blog/tags")
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
    fun retrieveTagListTest() {
        given(taggedArticleService.retrieveTagList(any()))
            .willReturn(tagListResponse())

        mockMvc.perform(
            get("/api/blog/tags?articleId=1")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("tagResponses").exists())
    }
}