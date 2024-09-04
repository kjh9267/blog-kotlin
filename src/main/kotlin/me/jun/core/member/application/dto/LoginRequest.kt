package me.jun.core.member.application.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    var email: String,
    @field:NotBlank
    var password: String
) {

}
