package me.jun.core.guestbook.application

import me.jun.core.guestbook.domain.PostCount
import me.jun.core.guestbook.domain.repository.PostCountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostCountService(
    private val postCountRepository : PostCountRepository
) {

    fun createPostCount(postId: Long?): PostCount {
        val postCount: PostCount = PostCount(
            postCountId = null,
            value = 0L,
            postId = postId!!,
            version = null
        )

        return postCountRepository.save(postCount)
    }

    fun incrementPostCount(postId: Long?): Long {
        val postCount: PostCount? = postCountRepository.findByPostId(postId)
        postCount!!.increment()
        return postCount.value
    }
}
