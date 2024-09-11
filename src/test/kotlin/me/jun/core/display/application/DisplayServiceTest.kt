package me.jun.core.display.application

import me.jun.core.display.application.dto.ArticleResponse
import me.jun.core.display.domain.repository.DisplayRepository
import me.jun.support.displayPageArticleResponse
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DisplayServiceTest {

    private lateinit var displayService: DisplayService

    @Mock
    private lateinit var displayRepositoryImpl: DisplayRepository<ArticleResponse>

    @BeforeEach
    fun setUp() {
        displayService = DisplayService(displayRepositoryImpl)
    }

    @Test
    fun retrieveDisplayTest() {
        given(displayRepositoryImpl.retrieveDisplay(any(), any()))
            .willReturn(displayPageArticleResponse())

        assertThat(displayService.retrieveDisplay(1, 10).page.numberOfElements)
            .isEqualTo(10)
    }
}