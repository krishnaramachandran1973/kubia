package com.cts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;

@Slf4j
@RestController
@SpringBootApplication
public class KubiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KubiaApplication.class, args);
    }

    @GetMapping
    public Mono<String> host(ServerWebExchange exchange) {
        InetAddress remoteAddress = exchange.getRequest()
                .getRemoteAddress()
                .getAddress();
        log.info("Received request from {}", remoteAddress);
        String hostName = exchange.getRequest()
                .getLocalAddress()
                .getHostName();
        return Mono.fromSupplier(() -> "You have hit " + hostName);
    }

}
