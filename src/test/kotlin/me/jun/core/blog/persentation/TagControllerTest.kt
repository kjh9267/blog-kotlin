package me.jun.core.blog.persentation

import com.fasterxml.jackson.databind.ObjectMapper
import me.jun.core.blog.application.TaggedArticleService
import me.jun.support.addTagRequest
import me.jun.support.tagListResponse
import me.jun.support.taggedArticleResponse
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
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

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun addTagToArticleTest() {
        val content: String = objectMapper.writeValueAsString(addTagRequest())

        given(taggedArticleService.addTagToArticle(any()))
            .willReturn(taggedArticleResponse())

        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("taggedArticleId").exists())
            .andExpect(jsonPath("tagId").exists())
            .andExpect(jsonPath("articleId").exists())
    }

    @Test
    fun noContent_addTagToArticleFailTest() {
        mockMvc.perform(
            post("/api/blog/tags")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
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
                .content(wrongContent)
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