package com.lineup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lineup")
public class LineupApplication {

    public static void main(String[] args) {
        SpringApplication.run(LineupApplication.class, args);
    }
}
