package com.alberto.boedo.modelo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class MapeaDataStoreImpl implements MapeaDataStore {

	@Autowired
	private MongoClient conexion;

	private Morphia morphia = new Morphia();
	private Datastore ds;
	@Value("Usuarios")
	private String dbName;
	// Class claseMapeo;

	@Override
	@Bean
	public Datastore mapeaDataStore() {
		morphia.map(Persona.class);
		ds = morphia.createDatastore(conexion, morphia.getMapper(), dbName);
		return ds;

	}

}
