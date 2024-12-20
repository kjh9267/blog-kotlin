package me.jun.core.guestbook.application

import me.jun.core.guestbook.application.dto.*
import me.jun.core.guestbook.application.exception.PostNotFoundException
import me.jun.core.guestbook.domain.Post
import me.jun.core.guestbook.domain.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val postCountService: PostCountService
) {

    fun createPost(request: CreatePostRequest?): PostResponse {
        val post: Post = request!!.toEntity()
        val savedPost: Post = postRepository.save(post)
        postCountService.createPostCount(savedPost.postId)
        return PostResponse.of(savedPost)
    }

    @Transactional(readOnly = true)
    fun retrievePost(request: RetrievePostRequest?): PostResponse {
        val post: Post = postRepository.findByPostId(request!!.postId)
            ?: throw PostNotFoundException.of(request.postId.toString())
        postCountService.incrementPostCount(post.postId)

        return PostResponse.of(post)
    }

    fun updatePost(request: UpdatePostRequest?): PostResponse {
        val post: Post = postRepository.findByPostId(request!!.postId)
            ?: throw PostNotFoundException.of(request.postId.toString())

        post.writer.validate(request.writerId)

        val updatedPost: Post = post.updatePostInfo(
            writerId = request.writerId,
            newTitle = request.newTitle,
            newContent = request.newContent
        )

        return PostResponse.of(updatedPost)
    }

    fun deletePost(request: DeletePostRequest?): Unit {
        val post: Post = postRepository.findByPostId(request!!.postId)
            ?: throw PostNotFoundException.of(request.postId.toString())

        post.writer.validate(request.writerId)

        postCountService.deletePostCount(post.postId)
        postRepository.deleteById(request.postId)
    }

    fun retrievePagedPosts(pageable: Pageable?): PagedPostResponse {
        val pagedPosts: Page<Post> = postRepository.findAllBy(pageable)
        return PagedPostResponse.of(pagedPosts)
    }
}
