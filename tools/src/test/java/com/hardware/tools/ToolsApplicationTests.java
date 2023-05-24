package com.hardware.tools;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Suite
@SelectPackages("com.hardware.tools")
class ToolsApplicationTests {

    @Test
    void contextLoads() {
    }

}
