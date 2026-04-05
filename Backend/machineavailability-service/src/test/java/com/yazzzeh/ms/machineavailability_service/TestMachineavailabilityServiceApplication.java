package com.yazzzeh.ms.machineavailability_service;

import org.springframework.boot.SpringApplication;

public class TestMachineavailabilityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MachineavailabilityServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
