package com.github.chocobe.sbb_mission2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles(value = "test")
class SbbMission2ApplicationTests {

	@Test
	void contextLoads() {
		boolean truthy = true;

        assertTrue(truthy);
	}

}
