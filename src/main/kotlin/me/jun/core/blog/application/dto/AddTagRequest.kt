package me.jun.core.blog.application.dto

data class AddTagRequest(
    var articleId: Long,
    var tagName: String
) {

}
