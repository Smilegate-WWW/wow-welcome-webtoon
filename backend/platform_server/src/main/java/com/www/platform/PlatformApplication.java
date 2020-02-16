package com.www.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@ComponentScan(basePackages="com.www.core") 밑에 @SpringBootA~~~(scanB~~~~)랑 동일
@EntityScan(basePackages = "com.www.core")
@EnableJpaRepositories(basePackages = "com.www.core")
@SpringBootApplication(scanBasePackages = "com.www.core")
public class PlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}