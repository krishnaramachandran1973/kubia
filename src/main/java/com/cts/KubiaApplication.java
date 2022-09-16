package com.cts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@SpringBootApplication
public class KubiaApplication {
    private AtomicInteger integer = new AtomicInteger(0);
    private static final String fileName = File.separator + "var" + File.separator + "data" + File.separator +
            "kubia.txt";

    public static void main(String[] args) {
        SpringApplication.run(KubiaApplication.class, args);
    }

    @GetMapping
    public Mono<String> host(ServerWebExchange exchange) {
        return Mono.fromSupplier(() -> "You have hit " + this.hostName(exchange));
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

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> fileWriter(@RequestBody String data, ServerWebExchange exchange) throws IOException {
        File file = new File(fileName);
        FileWriter out = new FileWriter(fileName, true);
        log.info("Received data {}", data);
        try {
            out.write(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Mono.fromSupplier(() -> "Data stored on pod " + this.hostName(exchange) + "\n");
    }

    @GetMapping(value = "/data", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> fileReader() throws IOException {
        BufferedReader br
                = new BufferedReader(new FileReader(fileName));
        String str;
        StringBuffer buffer = new StringBuffer();
        while ((str = br.readLine()) != null) {
            buffer.append(str);
        }
        br.close();
        return Mono.just(buffer.toString());
    }

    private String hostName(ServerWebExchange exchange) {
        InetAddress remoteAddress = exchange.getRequest()
                .getRemoteAddress()
                .getAddress();
        log.info("Received request from {}", remoteAddress);
        return exchange.getRequest()
                .getLocalAddress()
                .getHostName();
    }

}
