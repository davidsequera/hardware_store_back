package com.hardware.auth

import org.junit.jupiter.api.Test
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Suite
@SelectPackages("com.hardware.auth.domain", "com.hardware.auth.presentation")
class AuthApplicationTests {

    @Test
    fun contextLoads() {
    }

}
