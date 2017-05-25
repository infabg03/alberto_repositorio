package com.alberto.boedo.filtros;

import java.util.List;

public interface IfiltradoEstatico {
	public List<String> conversor(String rutaImagenSeleccionada, String color, String passwd);

	public void aumentarValor();

	public void disminuirValor();

	public void setValorOriginal();
}
