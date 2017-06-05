package com.alberto.boedo.modelo;

public interface PersonaDAO {

	public void addPersona(Persona p);

	public Persona getPersona(String mail);

	public void deletePersona(String mail);

	public int contarPersonas(String email);

}
