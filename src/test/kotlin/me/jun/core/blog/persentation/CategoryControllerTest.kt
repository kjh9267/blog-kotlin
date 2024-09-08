package me.jun.core.blog.persentation

import me.jun.core.blog.application.CategoryArticleService
import me.jun.core.blog.application.CategoryService
import me.jun.support.pagedArticleResponse
import me.jun.support.pagedCategoryResponse
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var categoryArticleService: CategoryArticleService

    @MockBean
    private lateinit var categoryService: CategoryService

    @Test
    fun retrievePagedCategoryArticlesTest() {
        given(categoryArticleService.retrievePagedCategoryArticles(any(), any()))
            .willReturn(pagedArticleResponse())

        mockMvc.perform(
            get("/api/blog/categories/category?page=0&size=10")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("articleResponses").exists())
            .andExpect(jsonPath("articleResponses.size").exists())
    }

    @Test
    fun retrievePagedCategoriesTest() {
        given(categoryService.retrievePagedCategories(any()))
            .willReturn(pagedCategoryResponse())

        mockMvc.perform(
            get("/api/blog/categories?page=0&size=10")
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("categoryResponses").exists())
            .andExpect(jsonPath("categoryResponses.size").exists())
    }
}