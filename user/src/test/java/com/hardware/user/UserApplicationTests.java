package com.hardware.user;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Suite
@SelectPackages("com.hardware.user")
class UserApplicationTests {

    @Test
    void contextLoads() {
    }

}
