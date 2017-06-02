package com.alberto.boedo.modelo;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaDAOImpl implements PersonaDAO {

	@Autowired
	private Datastore ds;

	@Override
	public void addPersona(Persona p) {
		this.ds.save(p);
	}

	@Override
	public Persona getPersona(String mail) {
		Query<Persona> query = this.ds.createQuery(Persona.class);
		query.field("_id").equal(mail);
		List<Persona> miLista = query.asList();
		if (miLista.size() > 0)
			return miLista.get(0);
		else
			return null;
	}

	@Override
	public void deletePersona(String mail) {
		Query<Persona> query = this.ds.createQuery(Persona.class);
		query.field("_id").equal(mail);

		ds.findAndDelete(query);

	}

}
