package me.jun.core.member.persentation

import com.fasterxml.jackson.databind.ObjectMapper
import me.jun.core.member.application.MemberService
import me.jun.support.memberResponse
import me.jun.support.registerRequest
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun registerTest() {
        val content: String = objectMapper.writeValueAsString(registerRequest())

        given(memberService.register(any()))
            .willReturn(memberResponse())

        mockMvc.perform(
            post("/api/member/register")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(content)
        )
            .andDo(print())
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("memberId").exists())
            .andExpect(jsonPath("name").exists())
            .andExpect(jsonPath("email").exists())
            .andExpect(jsonPath("role").exists())
            .andExpect(jsonPath("createdAt").exists())
            .andExpect(jsonPath("updatedAt").exists())
    }

    @Test
    fun noContent_registerFailTest() {
        mockMvc.perform(
            post("/api/member/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }

    @Test
    fun wrongContent_registerFailTest() {
        val wrongContent: String = objectMapper.writeValueAsString(
            registerRequest().apply {
                this.email = "asdf"
            }
        )

        mockMvc.perform(
            post("/api/member/register")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(wrongContent)
        )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("detail").exists())
    }
}