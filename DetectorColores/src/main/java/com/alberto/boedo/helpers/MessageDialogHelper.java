package com.alberto.boedo.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogHelper {

	/**
	 * Crea una ventana de dialogo con un boton de Aceptar.
	 * 
	 * @param shell
	 *            Ventana.
	 * @param titulo
	 *            Titulo que se pondra a la ventana.
	 * @param mensaje
	 *            Mensaje a mostrar en la ventana.
	 */
	public static void aceptarDialog(Shell shell, String titulo, String mensaje) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		dialog.setText(titulo);
		dialog.setMessage(mensaje);

		// open dialog and await user selection
		dialog.open();
	}

	/**
	 * Crea una ventana de dialogo con un boton de Aceptar y otro de Cancelar.
	 * 
	 * @param shell
	 *            Ventana.
	 * @param titulo
	 *            Titulo que se pondra a la ventana.
	 * @param mensaje
	 *            Mensaje a mostrar en la ventana.
	 */
	public static void aceptarCancelarDialog(Shell shell, String titulo, String mensaje) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.CANCEL);
		dialog.setText(titulo);
		dialog.setMessage(mensaje);

		// open dialog and await user selection
		dialog.open();
	}

}
