package me.jun.core.display.application.dto

data class ArticleResponse(
    var articleId: Long,
    var title: String,
    var content: String,
    var categoryName: String,
    var writerName: String
) {

    override fun toString(): String {
        return "ArticleResponse(articleId=$articleId, title='$title', content='$content', categoryName='$categoryName', writerName='$writerName')"
    }
}