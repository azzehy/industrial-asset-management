package com.yazzzeh.ms.machine;

import org.springframework.boot.SpringApplication;

public class TestMachineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MachineServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
