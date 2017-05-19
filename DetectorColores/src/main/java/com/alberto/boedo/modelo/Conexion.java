package com.alberto.boedo.modelo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class Conexion {

	@Bean
	public MongoClient getMongoClient() {
		return new MongoClient();
	}

}