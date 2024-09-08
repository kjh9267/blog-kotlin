package me.jun.core.blog.persentation

import me.jun.core.blog.application.CategoryArticleService
import me.jun.core.blog.application.CategoryService
import me.jun.core.blog.application.dto.PagedArticleResponse
import me.jun.core.blog.application.dto.PagedCategoryResponse
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/blog/categories")
class CategoryController(
    private val categoryArticleService: CategoryArticleService,
    private val categoryService: CategoryService
) {

    @GetMapping(
        value = ["/{categoryName}"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrievePagedCategoryArticles(
        @PathVariable(value = "categoryName") categoryName: String,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<PagedArticleResponse> {
        val pagedArticleResponse = categoryArticleService.retrievePagedCategoryArticles(
            categoryName = categoryName,
            pageable = PageRequest.of(page, size)
        )

        return ResponseEntity.ok()
            .body(pagedArticleResponse)
    }

    @GetMapping(
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrievePagedCategories(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): ResponseEntity<PagedCategoryResponse> {
        val pagedCategoryResponse = categoryService.retrievePagedCategories(PageRequest.of(page, size))
        return ResponseEntity.ok()
            .body(pagedCategoryResponse)
    }
}