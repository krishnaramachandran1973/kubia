package com.cts;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Person;
import com.cts.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@SpringBootApplication
public class KubiaApplication {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(KubiaApplication.class, args);
	}

	@GetMapping("/")
	public ResponseEntity<?> init() throws UnknownHostException {
		String hostName = InetAddress.getLocalHost()
				.getHostName();
		log.info("Executing init() from host {}", hostName);
		return ResponseEntity.ok(Collections.singletonMap("hostname", hostName));
	}

	@GetMapping("/persons")
	public List<Person> getPerson() {
		log.info("Retrieving Person");
		return personRepository.findAll();
	}

	@Bean
	CommandLineRunner init(PersonRepository personRepository) {
		return args -> {
			personRepository.save(Person.builder()
					.name("Krishnakumar")
					.age(48)
					.occupation("IT Professional")
					.build());
			personRepository.save(Person.builder()
					.name("Sasikala")
					.age(45)
					.occupation("House Wife")
					.build());
		};
	}

}
