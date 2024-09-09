package me.jun.core.guestbook.persentation

import com.fasterxml.jackson.databind.ObjectMapper
import me.jun.common.security.JwtProvider
import me.jun.common.security.MemberIdExtractor
import me.jun.common.security.exception.InvalidTokenException
import me.jun.core.guestbook.application.PostService
import me.jun.core.guestbook.application.exception.PostNotFoundException
import me.jun.support.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.doNothing
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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

    @Test
    fun retrievePostTest() {
        given(postService.retrievePost(any()))
            .willReturn(postResponse())

        mockMvc.perform(
            get("/api/guestbook/posts/1")
                .accept(APPLICATION_JSON)
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
    fun wrongPathVariable_retrievePostFailTest() {
        mockMvc.perform(
            get("/api/guestbook/posts/asdf")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun noPost_retrievePostFailTest() {
        given(postService.retrievePost(any()))
            .willThrow(PostNotFoundException.of(POST_ID.toString()))

        mockMvc.perform(
            get("/api/guestbook/posts/1")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun updatePostTest() {
        val content: String = objectMapper.writeValueAsString(updatePostRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        given(postService.updatePost(any()))
            .willReturn(updatedPostResponse())

        mockMvc.perform(
            put("/api/guestbook/posts")
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
    fun noToken_updatePostFailTest() {
        val content: String = objectMapper.writeValueAsString(updatePostRequest())

        mockMvc.perform(
            put("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun invalidToken_updatePostFailTest() {
        val content: String = objectMapper.writeValueAsString(updatePostRequest())

        given(jwtProvider.extractSubject(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            put("/api/guestbook/posts")
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
    fun noContent_updatePostFailTest() {
        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        mockMvc.perform(
            put("/api/guestbook/posts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun wrongContent_updatePostFailTest() {
        val wrongContent: String = objectMapper.writeValueAsString(
            updatePostRequest().apply {
                this.postId = 0L
            }
        )

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        mockMvc.perform(
            put("/api/guestbook/posts")
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
    fun unknownWriter_updatePostFailTest() {
        val content: String = objectMapper.writeValueAsString(updatePostRequest())

        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            put("/api/guestbook/posts")
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
    fun deletePostTest() {
        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willReturn(MEMBER_ID)

        doNothing()
            .`when`(postService)
            .deletePost(any())

        mockMvc.perform(
            delete("/api/guestbook/posts/1")
                .header(AUTHORIZATION, TOKEN)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun wrongPathVariable_deletePostFailTest() {
        mockMvc.perform(
            delete("/api/guestbook/posts/asdf")
                .header(AUTHORIZATION, TOKEN)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun noToken_deletePostFailTest() {
        mockMvc.perform(
            delete("/api/guestbook/posts/1")
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun invalidToken_deletePostFailTest() {
        given(jwtProvider.extractSubject(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            delete("/api/guestbook/posts/1")
                .header(AUTHORIZATION, TOKEN)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun unknownWriter_deletePostFailTest() {
        given(jwtProvider.extractSubject(any()))
            .willReturn(EMAIL)

        given(memberIdExtractor.extractMemberId(any()))
            .willThrow(InvalidTokenException.of(TOKEN))

        mockMvc.perform(
            delete("/api/guestbook/posts/1")
                .header(AUTHORIZATION, TOKEN)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun retrievePagedPostsTest() {
        given(postService.retrievePagedPosts(any()))
            .willReturn(pagedPostResponse())

        mockMvc.perform(
            get("/api/guestbook/posts/query?page=0&size=10")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("postResponses").exists())
            .andExpect(jsonPath("postResponses.size").exists())
    }
}