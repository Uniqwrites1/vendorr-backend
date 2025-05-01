package com.vendorr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VendorrApplication {
    public static void main(String[] args) {
        SpringApplication.run(VendorrApplication.class, args);
    }
}
