package Helpers;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;

import Ventanas.VentanaSelectora;

public class GoBackHelper {

	public static void funcionAtras(final Shell shell, ToolItem itemBack, final String passwd) {
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
