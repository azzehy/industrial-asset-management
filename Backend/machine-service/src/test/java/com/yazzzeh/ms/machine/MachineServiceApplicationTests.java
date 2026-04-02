package com.yazzzeh.ms.machine;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.mongodb.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MachineServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}



	@Test
	void shouldRegisterMachine() {
		String requestBody = """
				{
				    "serialNumber": "MCH-2024-005",
				    "model": "Conveyor Belt C300",
				    "manufacturer": "Fenner Dunlop",
				    "type": "MATERIAL_HANDLING",
				    "vesselOrLocation": "Mining Site B",
				    "installationDate": "2024-03-22"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/machine")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("serialNumber", Matchers.equalTo("MCH-2024-005"))
				.body("model", Matchers.equalTo("Conveyor Belt C300"))
				.body("type", Matchers.equalTo("MATERIAL_HANDLING"));
	}

}
