package com.alberto.boedo.modelo;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;

@Repository
public class PersonaDAOImpl implements PersonaDAO {

	MongoClient conexion;
	Morphia morphia = new Morphia();
	Datastore ds;

	public void mapeaDataStore() {

		morphia.map(Persona.class);
		ds = morphia.createDatastore(conexion, morphia.getMapper(), "Usuarios");
	}

	@Override
	public void addPersona(Persona p) {
		mapeaDataStore();
		ds.save(p);
	}

	@Override
	public Persona getPersona(String mail) {
		mapeaDataStore();
		Query<Persona> query = ds.createQuery(Persona.class);
		query.field("_id").equal(mail);
		List<Persona> miLista = query.asList();
		if (miLista.size() > 0)
			return miLista.get(0);
		else
			return null;
	}

	@Override
	public void deletePersona(String mail) {

		mapeaDataStore();
		Query<Persona> query = ds.createQuery(Persona.class);
		query.field("_id").equal(mail);

		ds.findAndDelete(query);

	}
}
