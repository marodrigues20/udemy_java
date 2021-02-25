package com.in28minutes.unittesting.unittesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


/**
 * @TestPropertySource(locations = {"classpath:test-configuration.properties"})
 * this test will use the test-configuration.properties file to set up the test
 * instead of using application.properties.
 */
@SpringBootTest
@TestPropertySource(locations = {"classpath:test-configuration.properties"})
class UnitTestingApplicationTests {

	@Test
	void contextLoads() {
	}

}
