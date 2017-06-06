package com.alberto.boedo.controlador;

import java.util.List;

public interface GestorEventos {

	/**
	 * Inserta un usuario en la base de datos.
	 * 
	 * @param campos
	 *            Lista que contiene los campos a insertar en el usuario.
	 */
	public void insertaUsuario(List<String> campos);

	/**
	 * Modifica los datos de un usuario en la base de datos.
	 * 
	 * @param valores
	 *            Lista que contiene los campos a insertar en el usuario.
	 * @param mail
	 *            Email del usuario que es la clave primaria en la base de
	 *            datos.
	 */
	public void modificoUsuario(List<String> valores, String mail);

	/**
	 * Comprueba que los datos requeridos para iniciar sesion sean correctos.
	 * 
	 * @param login
	 *            Email del usuario que es la clave primaria en la base de
	 *            datos.
	 * @param passwd
	 *            Contraseña del usuario.
	 * @return Valor booleano correspondiente a una comprobacion satisfactoria o
	 *         no.
	 */
	public boolean correctLogin(String login, String passwd);

	/**
	 * Inserta la ruta de una foto guardada por el usuario en la base de datos.
	 * 
	 * @param mail
	 *            Email del usuario que es la clave primaria en la base de
	 *            datos.
	 * @param rutaFoto
	 *            Ruta de la foto a almacenar.
	 */
	public void insertarFoto(String mail, String rutaFoto);

	/**
	 * Comprueba si un usuario tiene fotos.
	 * 
	 * @param mail
	 *            Email del usuario que es la clave primaria en la base de
	 *            datos.
	 * @return Valor booleano correspondiente a si el usuario tiene o no fotos.
	 */
	public boolean tieneFotos(String mail);

	/**
	 * Comprueba si un usuario existe en la base de datos.
	 * 
	 * @param mail
	 *            Email del usuario que es la clave primaria en la base de
	 *            datos.
	 * @return Valor booleano correspondiente a la existencia del usuario en la
	 *         base de datos.
	 */
	public boolean usuarioExiste(String mail);
}
