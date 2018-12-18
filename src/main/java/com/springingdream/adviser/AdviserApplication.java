package com.springingdream.adviser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		AdviserApplication.class,
		Jsr310JpaConverters.class
})
public class AdviserApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdviserApplication.class, args);
	}
}
