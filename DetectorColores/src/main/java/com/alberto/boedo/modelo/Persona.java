package com.alberto.boedo.modelo;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(noClassnameStored = true)
public class Persona {

	private String nombre;
	private String apellidos;
	@Id
	private String email;
	private String password;
	private String telefono;

	@Embedded
	private List<Foto> fotos;

	/**
	 * Crea una persona.
	 */
	public Persona() {
	}

	/**
	 * Obtiene el nombre de una persona.
	 * 
	 * @return El nombre de una persona.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre a una persona.
	 * 
	 * @param nombre
	 *            Nombre a setear.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene los apellidos de una persona.
	 * 
	 * @return Los apellidos de una persona.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Setea los apellidos de una persona.
	 * 
	 * @param apellidos
	 *            Los apellidos a setear.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve el email de una persona.
	 * 
	 * @return El email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setea el email a una persona.
	 * 
	 * @param email
	 *            El email a setear.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene el password de una persona.
	 * 
	 * @return El password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setea el password a una persona.
	 * 
	 * @param password
	 *            El password a setear.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve el telefono de una persona.
	 * 
	 * @return El telefono.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Setea el telefono a una persona.
	 * 
	 * @param telefono
	 *            El telefono a setear.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * 
	 * @return
	 */
	public List<Foto> getFotos() {
		return fotos;
	}

	/**
	 * 
	 * @param fotos
	 */
	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

}
