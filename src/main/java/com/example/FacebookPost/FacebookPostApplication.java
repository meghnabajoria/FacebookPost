package com.example.FacebookPost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FacebookPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookPostApplication.class, args);
	}

}
