package me.jun.core.guestbook.persentation

import com.fasterxml.jackson.databind.ObjectMapper
import me.jun.common.security.JwtProvider
import me.jun.common.security.MemberIdExtractor
import me.jun.common.security.exception.InvalidTokenException
import me.jun.core.guestbook.application.PostService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var jwtProvider: JwtProvider

    @MockBean
    private lateinit var memberIdExtractor: MemberIdExtractor

    @MockBean
    private lateinit var postService: PostService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun createPostTest() {
        val content: String = objectMapper.writeValueAsString(createPostRequest())

        given(postService.createPost(any()))
            .willReturn(postResponse())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        mockMvc.perform(
            post("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("postId").exists())
            .andExpect(jsonPath("title").exists())
            .andExpect(jsonPath("content").exists())
            .andExpect(jsonPath("writerId").exists())
            .andExpect(jsonPath("createdAt").exists())
            .andExpect(jsonPath("updatedAt").exists())
    }

    @Test
    fun noToken_createPostFailTest() {
        mockMvc.perform(
            post("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun invalidToken_createPostFailTest() {
        val content: String = objectMapper.writeValueAsString(createPostRequest())

        given(jwtProvider.extractSubject(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            post("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun wrongContent_createPostFailTest() {
        val wrongContent: String = objectMapper.writeValueAsString(createPostRequest().apply {
            this.title = ""
        })

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        mockMvc.perform(
            post("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(wrongContent)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun noContent_createPostFailTest() {
        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        mockMvc.perform(
            post("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun unknownWriter_createPostFailTest() {
        val content: String = objectMapper.writeValueAsString(createPostRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            post("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }
}