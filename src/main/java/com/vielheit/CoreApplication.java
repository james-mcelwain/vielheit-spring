package com.vielheit;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {
	public CoreApplication() {}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
}
