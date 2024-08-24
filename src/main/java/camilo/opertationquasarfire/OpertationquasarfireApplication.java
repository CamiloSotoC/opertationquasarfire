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
						.title("Operation Quasar Fire - API")
						.description("API Operation Quasar Fire"));
	}
}
