package com.jgmedellin.cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Enable JPA Auditing and set the AuditorAware bean name
@OpenAPIDefinition( // OpenAPI 3.0 definition annotation to create the OpenAPI documentation with Swagger
				info = @Info(
								title = "Card microservice REST API",
								description = "EazyBank Cards microservice REST API Documentation",
								version = "v1",
								contact = @Contact(
												name = "Gerardo Medellin",
												email = "thegera4@hotmail.com",
												url = "https://www.jgmedellin.com"
								),
								license = @License(
												name = "Apache 2.0",
												url = "http://www.apache.org/licenses/LICENSE-2.0"
								)
				),
				externalDocs = @ExternalDocumentation(
								description = "GitHub Repository",
								url = "https://github.com/thegera4/springboot-cards-microservice"
				)

)
public class CardsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}
}