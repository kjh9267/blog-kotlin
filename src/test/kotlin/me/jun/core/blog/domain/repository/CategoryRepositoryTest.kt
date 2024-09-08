package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Category
import me.jun.support.CATEGORY_ID
import me.jun.support.CATEGORY_NAME
import me.jun.support.category
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryRepositoryTest {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Test
    fun findByNameTest() {
        val expected: Category = category()

        categoryRepository.save(
            category().apply {
                this.categoryId = null
            }
        )

        assertThat(categoryRepository.findByName(CATEGORY_NAME))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noCategory_findByNameFailTest() {
        assertThat(categoryRepository.findByName(CATEGORY_NAME))
            .isNull()
    }

    @Test
    fun findByCategoryIdTest() {
        val expected: Category = category()

        categoryRepository.save(
            category().apply {
                this.categoryId = null
            }
        )

        assertThat(categoryRepository.findByCategoryId(CATEGORY_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noCategory_findByCategoryIdFailTest() {
        assertThat(categoryRepository.findByCategoryId(CATEGORY_ID))
            .isNull()
    }

    @Test
    fun findAllByTest() {
        for (id in 1..20) {
            categoryRepository.save(
                category().apply {
                    this.categoryId = id.toLong()
                }
            )
        }

        val page: Page<Category> = categoryRepository.findAllBy(PageRequest.of(0, 10))
        assertThat(page.numberOfElements).isEqualTo(10)
    }
}