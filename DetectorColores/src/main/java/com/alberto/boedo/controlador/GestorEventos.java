package com.alberto.boedo.controlador;

import java.util.List;

public interface GestorEventos {
	public void insertaUsuario(List<String> campos);

	public void modificoUsuario(List<String> valores, String mail);

	public boolean correctLogin(String login, String passwd);
}
