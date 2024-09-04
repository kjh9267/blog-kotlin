package me.jun.core.blog.domain.service

import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Category
import org.springframework.stereotype.Component

@Component
class CategoryMatchingService {

    fun matchCategory(article: Article?, category: Category?): Unit {
        article!!.categoryId = category!!.categoryId
        category.incrementMappedArticleCount()
    }

    fun changeCategory(article: Article?, newCategory: Category?, oldCategory: Category?): Unit {
        article!!.categoryId = newCategory!!.categoryId
        newCategory.incrementMappedArticleCount()
        oldCategory!!.decrementMappedArticleCount()
    }
}