package camilo.opertationquasarfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class OpertationquasarfireApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpertationquasarfireApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API - Operation Quasar Fire")
						.description("This repository contains the implementation of the 'Operation Quasar Fire' challenge in Java with SpringBoot."
						+"<br>"
						+"The goal is to create a program that returns the location and rebuild the distress message of an Imperial cargo ship adrift in an asteroid field."));
	}
}
