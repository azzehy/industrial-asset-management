package com.yazzzeh.ms.workorder_service;

import org.springframework.boot.SpringApplication;

public class TestWorkorderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(WorkorderServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
