package me.jun.core.blog.application

import me.jun.core.blog.application.exception.TagNotFoundException
import me.jun.core.blog.domain.Tag
import me.jun.core.blog.domain.repository.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TagService(
    private val tagRepository: TagRepository
) {

    fun createTagOrElseGet(tagName: String?): Tag {
        val tag: Tag = Tag(
            tagId = null,
            name = tagName!!
        )

        return tagRepository.findByName(tagName)
            ?: tagRepository.save(tag)
    }

    fun retrieveTag(tagId: Long): Tag {
        return tagRepository.findByTagId(tagId)
            ?: throw TagNotFoundException.of(tagId.toString())
    }
}
