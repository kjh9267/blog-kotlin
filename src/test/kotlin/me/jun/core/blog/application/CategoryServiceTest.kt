package me.jun.core.blog.application

import me.jun.core.blog.application.dto.CategoryResponse
import me.jun.core.blog.domain.repository.CategoryRepository
import me.jun.support.category
import me.jun.support.categoryResponse
import me.jun.support.createCategoryRequest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

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
        val expected: CategoryResponse = categoryResponse()

        given(categoryRepository.findByName(any()))
            .willReturn(category())

        assertThat(categoryService.createCategoryOrElseGet(createCategoryRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun createCategoryTest() {
        val expected: CategoryResponse = categoryResponse()

        given(categoryRepository.findByName(any()))
            .willReturn(null)

        given(categoryRepository.save(any()))
            .willReturn(category())

        assertThat(categoryService.createCategoryOrElseGet(createCategoryRequest()))
            .isEqualToComparingFieldByField(expected)
    }
}