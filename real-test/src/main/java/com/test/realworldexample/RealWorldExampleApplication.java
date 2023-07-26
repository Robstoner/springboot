package com.test.realworldexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RealWorldExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealWorldExampleApplication.class, args);
	}

}
