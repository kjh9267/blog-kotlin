package me.jun.core.blog.domain

data class ArticleInfo(
    var title: String,
    var content: String
) {
    fun updateTitle(newTitle: String): ArticleInfo {
        this.title = newTitle
        return this;
    }

    fun updateContent(newContent: String): ArticleInfo {
        this.content = newContent;
        return this;
    }
}
