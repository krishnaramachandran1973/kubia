package com.cts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@SpringBootApplication
public class KubiaApplication {
    private AtomicInteger integer = new AtomicInteger(0);

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

    @GetMapping("/alive")
    public ResponseEntity<Mono<String>> alive() {
        if (integer.incrementAndGet() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Mono.just("app not alive"));
        }
        return ResponseEntity.ok()
                .body(Mono.just("ok"));
    }

}
