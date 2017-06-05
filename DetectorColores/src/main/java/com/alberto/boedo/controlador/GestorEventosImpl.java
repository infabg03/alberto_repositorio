package com.alberto.boedo.controlador;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alberto.boedo.modelo.Foto;
import com.alberto.boedo.modelo.Persona;
import com.alberto.boedo.modelo.PersonaDAO;
import com.alberto.boedo.naming.i18Message;
import com.alberto.boedo.vista.VentanaPrincipal;

@Component
public class GestorEventosImpl implements GestorEventos {

	@Autowired
	private PersonaDAO personaDAO;

	private final static Logger log = Logger.getLogger(GestorEventosImpl.class);

	@Override
	public void insertaUsuario(List<String> campos) {
		List<Foto> rutas = new ArrayList<Foto>();
		Foto f1 = new Foto();
		f1.setRuta("desktop");
		rutas.add(f1);
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
			log.warn(i18Message.MSG_EMAIL_VACIO);
			return false;
		}

	}

	@Override
	public void insertarFoto(String mail, String rutaFoto) {
		Persona p = personaDAO.getPersona(mail);
		List<Foto> fotos = p.getFotos();
		Foto nuevaFoto = new Foto();
		nuevaFoto.setRuta(rutaFoto);
		fotos.add(nuevaFoto);
		personaDAO.deletePersona(mail);
		personaDAO.addPersona(p);
	}

	@Override
	public boolean tieneFotos(String mail) {
		Persona p = personaDAO.getPersona(mail);
		List<Foto> fotos = p.getFotos();
		return fotos.size() > 1;
	}

	@Override
	public boolean usuarioExiste(String mail) {
		int cantidad = personaDAO.contarPersonas(mail);
		return cantidad == 0 ? false : true;
	}

}
