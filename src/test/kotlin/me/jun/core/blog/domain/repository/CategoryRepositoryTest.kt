package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Category
import me.jun.support.CATEGORY_NAME
import me.jun.support.category
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
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
}