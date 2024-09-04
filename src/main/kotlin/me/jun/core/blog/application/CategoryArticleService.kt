package me.jun.core.blog.application

import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.core.blog.domain.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryArticleService(
    private val categoryRepository: CategoryRepository,
    private val articleRepository: ArticleRepository
) {


}
