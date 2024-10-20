package camilo.opertationquasarfire;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import camilo.opertationquasarfire.entities.RoleEntity;
import camilo.opertationquasarfire.entities.UserEntity;
import camilo.opertationquasarfire.models.ERole;
import camilo.opertationquasarfire.repositories.UserRepository;
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
						.title("Springboot - API - Operation Quasar Fire")
						.description(
								"This repository contains the implementation of the 'Operation Quasar Fire' challenge in Java with SpringBoot."
										+ "<br>"
										+ "The goal is to create a program that returns the location and rebuild the distress message of an Imperial cargo ship adrift in an asteroid field."));
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;

	@Bean
	CommandLineRunner init() {
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("camilo@gmail.com")
					.username("camilo")
					.password(passwordEncoder.encode("password"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();
			UserEntity userEntity2 = UserEntity.builder()
					.email("ignacio@gmail.com")
					.username("ignacio")
					.password(passwordEncoder.encode("password"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();
			UserEntity userEntity3 = UserEntity.builder()
					.email("jhon@gmail.com")
					.username("jhon")
					.password(passwordEncoder.encode("password"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.GUEST.name()))
							.build()))
					.build();

			this.userRepository.save(userEntity);
			this.userRepository.save(userEntity2);
			this.userRepository.save(userEntity3);
		};
	}
}
