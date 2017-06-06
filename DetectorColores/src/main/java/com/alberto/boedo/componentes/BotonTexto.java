package com.alberto.boedo.componentes;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class BotonTexto {

	/**
	 * Crea un boton con un texto asociado.
	 * 
	 * @param parent
	 *            El componente padre del que colgara el boton.
	 * @param estilo
	 *            Estilo que tendra el boton.
	 * @param texto
	 *            Texto que tendra el boton.
	 * @return Un boton con texto.
	 */
	public Button devuelveBotonTexto(Composite parent, int estilo, String texto) {
		Button button = new Button(parent, estilo);
		button.setText(texto);
		return button;

	}

}
