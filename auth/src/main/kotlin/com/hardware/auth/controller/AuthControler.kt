package com.hardware.auth.controller

import com.hardware.auth.domain.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class AuthControler(@Autowired val authService: AuthService) {
    @QueryMapping
    fun authenicate(a: String ): String {
        return "Hello World"
    }

}