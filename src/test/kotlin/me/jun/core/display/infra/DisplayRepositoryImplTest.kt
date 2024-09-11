package me.jun.core.display.infra

import me.jun.core.blog.application.ArticleService
import me.jun.core.display.application.dto.ArticleResponse
import me.jun.core.display.domain.repository.DisplayRepository
import me.jun.core.member.application.MemberService
import me.jun.support.createArticleRequest
import me.jun.support.registerRequest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class DisplayRepositoryImplTest {

    @Autowired
    private lateinit var displayRepositoryImpl: DisplayRepository<ArticleResponse>

    @Autowired
    private lateinit var articleService: ArticleService

    @Autowired
    private lateinit var memberService: MemberService

    @Test
    fun retrieveDisplayTest() {
        memberService.register(registerRequest())

        for (count in 1..20) {
            articleService.createArticle(createArticleRequest())
        }

        val page: Page<ArticleResponse> = displayRepositoryImpl.retrieveDisplay(1, 10)

        assertAll(
            {
                assertThat(page.totalElements)
                    .isEqualTo(10)
            },
            {
                assertThat(page.totalPages)
                    .isEqualTo(1)
            }
        )
    }

    @Test
    fun noArticle_retrieveDisplayFailTest() {
        memberService.register(registerRequest())

        val page: Page<ArticleResponse> = displayRepositoryImpl.retrieveDisplay(1, 10)

        assertThat(page.numberOfElements)
            .isEqualTo(0)
    }
}