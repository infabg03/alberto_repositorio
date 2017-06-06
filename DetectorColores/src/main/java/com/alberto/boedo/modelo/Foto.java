package com.alberto.boedo.modelo;

import java.io.Serializable;

public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ruta;

	/**
	 * Obtiene la ruta de una foto.
	 * 
	 * @return La ruta de una foto.
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * Setea la ruta a una foto.
	 * 
	 * @param ruta
	 *            Ruta a setear a la foto.
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}
