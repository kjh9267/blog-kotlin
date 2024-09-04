package me.jun.core.member.application.dto

import jakarta.validation.constraints.NotBlank

data class RetrieveMemberRequest(
    @field:NotBlank
    var email: String
) {

}
