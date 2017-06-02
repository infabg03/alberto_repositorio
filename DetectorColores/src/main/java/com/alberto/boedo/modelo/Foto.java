package com.alberto.boedo.modelo;

import java.io.Serializable;

public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ruta;

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}
