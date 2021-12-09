package com.cts;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@SpringBootApplication
public class KubiaApplication {

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

}
