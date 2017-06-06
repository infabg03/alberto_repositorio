package com.alberto.boedo.filtros;

import java.util.List;

public interface IfiltradoEstatico {

	/**
	 * Filtra una imagen segun un color seleccionado;
	 * 
	 * @param rutaImagenSeleccionada
	 *            Ruta de la imagen que se va a filtrar.
	 * @param color
	 *            Color por el que se va a filtrar.
	 * @param passwd
	 *            Email del usuario.
	 * @return Lista con el par de rutas imagen original/imagen filtrada.
	 */
	public List<String> conversor(String rutaImagenSeleccionada, String color, String passwd);

	/**
	 * Aumenta el valor del atributo que controla la amplitud de espectro del
	 * color.
	 * 
	 */
	public void aumentarValor();

	/**
	 * Disminuye el valor del atributo que controla la amplitud de espectro del
	 * color.
	 * 
	 */
	public void disminuirValor();

	/**
	 * Setea al valor original del atributo que controla la amplitud de espectro
	 * del color.
	 * 
	 */
	public void setValorOriginal();
}
