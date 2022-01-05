package com.boot.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShellBootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShellBootWebApplication.class, args);
    }

}
