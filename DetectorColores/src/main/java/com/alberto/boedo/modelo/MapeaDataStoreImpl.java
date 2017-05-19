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
	private Class claseMapeo = Persona.class;

	@Override
	@Bean
	public Datastore mapeaDataStore() {
		morphia.map(claseMapeo);
		ds = morphia.createDatastore(conexion, morphia.getMapper(), dbName);
		return ds;

	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Class getClaseMapeo() {
		return claseMapeo;
	}

	public void setClaseMapeo(Class claseMapeo) {
		this.claseMapeo = claseMapeo;
	}

}
