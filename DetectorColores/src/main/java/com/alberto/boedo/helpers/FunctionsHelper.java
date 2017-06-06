package com.alberto.boedo.helpers;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.springframework.stereotype.Component;

import com.alberto.boedo.vista.VentanaSelectora;

@Component
public class FunctionsHelper {

	/**
	 * Vuelve a la pantalla anterior a la que nos encontramos.
	 * 
	 * @param shell
	 *            Pantalla actual.
	 * @param itemBack
	 *            El boton de vuelta atras.
	 * @param passwd
	 *            Email del usuario.
	 */
	public void funcionAtras(final Shell shell, ToolItem itemBack, final String passwd) {
		itemBack.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				Thread tVentanaSeleccion = new Thread(new VentanaSelectora(passwd));
				tVentanaSeleccion.start();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
