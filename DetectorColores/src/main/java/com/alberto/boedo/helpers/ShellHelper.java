package com.alberto.boedo.helpers;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.springframework.stereotype.Component;

@Component
public class ShellHelper {

	/**
	 * Evita que se cierre la ventana al pulsar el boton X.
	 * 
	 * @param shell
	 *            Ventana.
	 */
	public void noCerrarShell(Shell shell) {
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
			}
		});
	}

	/**
	 * Centra la ventana.
	 * 
	 * @param display
	 *            Display asociado a la ventana.
	 * @param shell
	 *            Ventana.
	 */
	public void centrarVentana(Display display, Shell shell) {
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);
	}
}
