package com.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
public class JwtauthenticationserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtauthenticationserverApplication.class, args);
	}

}
