package com.hardware.auth.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EncryptComponentTest(
    @Autowired
    private val encryptComponent: EncryptComponent
) {
    @ParameterizedTest
    @ValueSource(strings = ["a", "b", "password", "other"])
    fun  testPasswordHashing(password: String?) {
        val hash: String = encryptComponent.hashPassword(password)
        Assertions.assertTrue(encryptComponent.verifyPassword(password, hash))
    }
}
