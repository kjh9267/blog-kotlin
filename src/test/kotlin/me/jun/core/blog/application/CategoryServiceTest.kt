package me.jun.core.blog.application

import me.jun.core.blog.application.dto.PagedCategoryResponse
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.repository.CategoryRepository
import me.jun.support.CATEGORY_NAME
import me.jun.support.category
import me.jun.support.pagedCategories
import me.jun.support.pagedCategoryResponse
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class CategoryServiceTest {

    private lateinit var categoryService: CategoryService

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @BeforeEach
    fun setUp() {
        categoryService = CategoryService(categoryRepository)
    }

    @Test
    fun getCategoryTest() {
        val expected: Category = category()

        given(categoryRepository.findByName(any()))
            .willReturn(category())

        assertThat(categoryService.createCategoryOrElseGet(CATEGORY_NAME))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun createCategoryTest() {
        val expected: Category = category()

        given(categoryRepository.findByName(any()))
            .willReturn(null)

        given(categoryRepository.save(any()))
            .willReturn(category())

        assertThat(categoryService.createCategoryOrElseGet(CATEGORY_NAME))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun retrievePagedCategoriesTest() {
        val expected: PagedCategoryResponse = pagedCategoryResponse()

        given(categoryRepository.findAllBy(any()))
            .willReturn(pagedCategories())

        assertThat(categoryService.retrievePagedCategories(PageRequest.of(0, 10)))
            .isEqualToComparingFieldByField(expected)
    }
}