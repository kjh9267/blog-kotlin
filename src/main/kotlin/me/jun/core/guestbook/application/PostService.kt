package me.jun.core.guestbook.application

import me.jun.core.guestbook.application.dto.*
import me.jun.core.guestbook.application.exception.PostNotFoundException
import me.jun.core.guestbook.domain.Post
import me.jun.core.guestbook.domain.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {

    fun createPost(request: CreatePostRequest?): PostResponse {
        val post: Post = request!!.toEntity()
        val savedPost: Post = postRepository.save(post)
        return PostResponse.of(savedPost)
    }

    fun retrievePost(request: RetrievePostRequest?): PostResponse {
        val post: Post = postRepository.findByPostId(request!!.postId)
            ?: throw PostNotFoundException.of(request.postId.toString())
        return PostResponse.of(post)
    }

    fun updatePost(request: UpdatePostRequest): PostResponse {
        val post: Post = postRepository.findByPostId(request.postId)
            ?: throw PostNotFoundException.of(request.postId.toString())

        val updatedPost = post.updateTitle(request.newTitle)
            .updateContent(request.newContent)

        return PostResponse.of(updatedPost)
    }

    fun deletePost(request: DeletePostRequest): Unit {
        postRepository.deleteById(request.postId)
    }
}
