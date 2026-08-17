package com.github.chocobe.sbb_mission2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SbbMission2Application {

	public static void main(String[] args) {
		SpringApplication.run(SbbMission2Application.class, args);
	}

}
