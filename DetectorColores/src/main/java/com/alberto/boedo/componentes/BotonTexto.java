package com.alberto.boedo.componentes;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class BotonTexto {

	public Button devuelveBotonTexto(Composite parent, int estilo, String texto) {
		Button button = new Button(parent, estilo);
		button.setText(texto);
		return button;

	}

}
