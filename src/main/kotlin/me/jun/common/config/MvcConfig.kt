package me.jun.common.config

import me.jun.common.security.JwtSubjectResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig(
    private val jwtSubjectResolver: JwtSubjectResolver
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtSubjectResolver)
        super.addArgumentResolvers(resolvers)
    }
}