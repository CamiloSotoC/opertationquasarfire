package camilo.opertationquasarfire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.swagger.v3.oas.models.OpenAPI;

@SpringBootTest
class OpertationquasarfireApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    void testCustomOpenAPI() {
        OpertationquasarfireApplication application = new OpertationquasarfireApplication();
        OpenAPI openAPI = application.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Springboot - API - Operation Quasar Fire", openAPI.getInfo().getTitle());
        assertTrue(openAPI.getInfo().getDescription().contains("This repository contains the implementation of the 'Operation Quasar Fire' challenge in Java with SpringBoot."));
    }

}
