package me.jun.core.guestbook.application

import me.jun.core.guestbook.application.dto.CreatePostRequest
import me.jun.core.guestbook.application.dto.PostResponse
import me.jun.core.guestbook.domain.Post
import me.jun.core.guestbook.domain.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {

    fun createPost(request: CreatePostRequest): PostResponse {
        val post: Post = request.toEntity()
        val savedPost: Post = postRepository.save(post)
        return PostResponse.of(savedPost)
    }

}
