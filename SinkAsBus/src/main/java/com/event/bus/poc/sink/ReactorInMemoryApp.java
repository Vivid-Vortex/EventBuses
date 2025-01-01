package com.event.bus.poc.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactorInMemoryApp {

	public static void main(String[] args) {
		SpringApplication.run(ReactorInMemoryApp.class, args);
	}
}
