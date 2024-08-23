package com.example.demo;

import java.awt.image.BufferedImage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
public class EmployeeDataApplication {
    @Bean
    HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
    	return new BufferedImageHttpMessageConverter();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeDataApplication.class, args);
	}
}
