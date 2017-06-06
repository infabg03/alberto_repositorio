package com.alberto.boedo.modelo;

public interface PersonaDAO {

	/**
	 * Añade una persona a la base de datos.
	 * 
	 * @param p
	 *            Persona a añadir.
	 */
	public void addPersona(Persona p);

	/**
	 * Obtiene una persona a traves de su email.
	 * 
	 * @param mail
	 *            Email por el que buscamos.
	 * @return Persona que se ha obtenido tras la busqueda por email.
	 */
	public Persona getPersona(String mail);

	/**
	 * Borra una persona a traves de su email.
	 * 
	 * @param mail
	 *            Email por el que vamos a borrar.
	 */
	public void deletePersona(String mail);

	/**
	 * Devuelve el numero de personas con un email en la BD [0,1]
	 * 
	 * @param email
	 * @return
	 */
	public int contarPersonas(String email);

}
