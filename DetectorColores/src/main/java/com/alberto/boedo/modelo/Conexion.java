package com.alberto.boedo.modelo;

import com.mongodb.MongoClient;

public class Conexion {
	public static MongoClient getMongoClient() {
		return new MongoClient();
	}

}