package com.www.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.www")
@EntityScan(basePackages = "com.www.core")
@EnableJpaRepositories(basePackages = "com.www.core")
@EnableConfigurationProperties
//@EnableJpaAuditing
<<<<<<< HEAD
@SpringBootApplication(scanBasePackages="com.www")
=======
>>>>>>> b3b385de70d3a30dfd65ad12cfd07fb2f3e65182
public class FileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServerApplication.class, args);
	}

}
