package com.alberto.boedo.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alberto.boedo.modelo.Persona;
import com.alberto.boedo.modelo.PersonaDAO;

@Component
public class GestorEventosImpl implements GestorEventos {
	@Autowired
	private PersonaDAO personaDAO;

	@Override
	public void insertaUsuario(List<String> campos) {
		List<String> rutas = new ArrayList<String>();

		Persona p = new Persona();
		p.setNombre(campos.get(0));
		p.setApellidos(campos.get(1));
		p.setEmail(campos.get(2));
		p.setPassword(campos.get(3));
		p.setFotos(rutas);
		p.setTelefono(campos.get(4));

		personaDAO.addPersona(p);
	}

	@Override
	public void modificoUsuario(List<String> valores, String mail) {
		personaDAO.deletePersona(mail);
		insertaUsuario(valores);
	}

	@Override
	public boolean correctLogin(String login, String passwd) {
		try {
			return login.matches(personaDAO.getPersona(login).getEmail())
					&& passwd.matches(personaDAO.getPersona(login).getPassword());
		} catch (NullPointerException e) {
			// System.out.println("Lista vacia");
			return false;
		}

	}

}
