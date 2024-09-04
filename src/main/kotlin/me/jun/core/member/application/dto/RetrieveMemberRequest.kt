package me.jun.core.member.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RetrieveMemberRequest(
    @field:NotBlank
    @field:Email
    var email: String
) {

}
