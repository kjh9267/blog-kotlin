package me.jun.core.display.persentation

import me.jun.core.display.application.DisplayService
import me.jun.core.display.application.dto.PagedArticleResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/display")
class DisplayController(
    private val displayService: DisplayService
) {

    @GetMapping(
        produces = [APPLICATION_JSON_VALUE]
    )
    fun display(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<PagedArticleResponse> {
        val pagedArticleResponse: PagedArticleResponse = displayService.retrieveDisplay(page, size)
        return ResponseEntity.ok()
            .body(pagedArticleResponse)
    }
}