package com.alberto.boedo.componentes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class BotonImagen {

	/**
	 * Crea un boton al que se le setea una imagen.
	 * 
	 * @param display
	 *            El display asociado a la ventana.
	 * @param parent
	 *            El componente padre del que colgara el boton.
	 * @param ruta
	 *            La ruta de la imagen que queremos mostrar en el boton.
	 * @return Boton que es una imagen.
	 */
	public Button getBotonImagen(Display display, Composite parent, String ruta) {

		Button button = new Button(parent, SWT.NONE);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setImage(new Image(display, new ImageData(ruta)));
		return button;

	}

}
