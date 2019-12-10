package com.example.demo;

import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * G6_Online application
 * 
 * @author Cole Weitzel, Taylor Weil
 *
 */

@SpringBootApplication
@EnableAutoConfiguration
public class G6OnlineBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(G6OnlineBackendApplication.class, args);
	
	}
	
	
	

}
