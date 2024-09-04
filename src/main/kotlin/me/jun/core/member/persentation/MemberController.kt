package me.jun.core.member.persentation

import jakarta.validation.Valid
import me.jun.core.member.application.MemberService
import me.jun.core.member.application.dto.LoginRequest
import me.jun.core.member.application.dto.MemberResponse
import me.jun.core.member.application.dto.RegisterRequest
import me.jun.core.member.application.dto.TokenResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping(
        value = ["/register"],
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun register(@RequestBody @Valid request: RegisterRequest): ResponseEntity<MemberResponse> {
        val memberResponse: MemberResponse = memberService.register(request)
        return ResponseEntity.ok()
            .body(memberResponse)
    }

    @PostMapping(
        value = ["/login"],
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<TokenResponse> {
        val tokenResponse: TokenResponse = memberService.login(request)
        return ResponseEntity.ok()
            .body(tokenResponse)
    }
}