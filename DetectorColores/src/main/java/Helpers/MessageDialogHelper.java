package Helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogHelper {

	public static void aceptarDialog(Shell shell, String titulo, String mensaje) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		dialog.setText(titulo);
		dialog.setMessage(mensaje);

		// open dialog and await user selection
		dialog.open();
	}

	public static void aceptarCancelarDialog(Shell shell, String titulo, String mensaje) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.CANCEL);
		dialog.setText(titulo);
		dialog.setMessage(mensaje);

		// open dialog and await user selection
		dialog.open();
	}

}
