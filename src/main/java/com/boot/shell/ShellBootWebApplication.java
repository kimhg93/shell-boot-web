package com.boot.shell;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class})
public class ShellBootWebApplication {

    public static void main(String[] args) {
        System.setProperty("log4jdbc.log4j2.properties.file", "/config/log4jdbc.log4j2.properties");
        SpringApplication.run(ShellBootWebApplication.class, args);
    }

}
