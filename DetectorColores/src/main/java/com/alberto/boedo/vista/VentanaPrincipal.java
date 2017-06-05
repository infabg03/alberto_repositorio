package com.alberto.boedo.vista;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.componentes.BotonTexto;
import com.alberto.boedo.componentes.LabeledEditText;
import com.alberto.boedo.controlador.GestorEventos;
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.MessageDialogHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaPrincipal implements Runnable {

	private GestorEventos gestor = BeansFactory.getBean(GestorEventos.class);
	private final static Logger log = Logger.getLogger(VentanaPrincipal.class);
	private static boolean ejecutado = false;

	private void getContent(final Shell shell) {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		composite.setLayoutData(gridData);
		composite.setBackground(ColorHelper.COLOR_WHITE);

		final LabeledEditText login = new LabeledEditText(composite, SWT.NONE, "", i18Message.lOGIN, false, 45);

		Composite compoPasswd = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		compoPasswd.setBackground(ColorHelper.COLOR_WHITE);
		compoPasswd.setLayout(gridLayout);

		final LabeledEditText passwd = new LabeledEditText(compoPasswd, SWT.NONE, "", i18Message.PASSWD, true, 50);

		Composite botonera = new Composite(shell, SWT.NONE);
		GridLayout gridLayoutBotonera = new GridLayout(1, false);
		gridLayoutBotonera.marginWidth = 5;
		gridLayoutBotonera.marginHeight = 5;
		gridLayoutBotonera.verticalSpacing = 0;
		gridLayoutBotonera.horizontalSpacing = 0;
		botonera.setLayout(gridLayoutBotonera);

		Button registro = new BotonTexto().devuelveBotonTexto(botonera, SWT.NONE, i18Message.lOGIN);
		registro.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (gestor.correctLogin(login.getTexto(), passwd.getTexto())) {
					Thread tVentanaSelectora = new Thread(new VentanaSelectora(login.getTexto()));
					Display.getCurrent().dispose();
					tVentanaSelectora.run();
				} else {
					MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_ACCESO, i18Message.MSG_ACCESO);

					login.setText("");
					passwd.setText("");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Composite compositeLink = new Composite(shell, SWT.NONE);
		compositeLink.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN, true, true));
		GridLayout layoutLink = new GridLayout(1, false);
		compositeLink.setLayout(layoutLink);

		final Link link = new Link(compositeLink, SWT.NONE);
		link.setText(i18Message.LINK_TXT);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						VentanaRegistroModal secondWindow = new VentanaRegistroModal(shell, false);
						secondWindow.open();

					}
				});
			}
		});
	}

	public static void main(String[] args) {
		final Display display = new Display();

		System.out.println("El valor de ejecutado es: " + ejecutado);
		if (!ejecutado) {
			// Levantamos el servidor
			try {
				Runtime.getRuntime().exec("src/resources/bat/ejecMongo.bat");
				ejecutado = true;
			} catch (IOException e1) {
				log.error(e1.getMessage());
			}
		}

		final VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		final Shell shell = new Shell(display, SWT.MIN);
		final ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(500, 300);
		shell.setBackgroundImage(new Image(display, new ImageData(i18Message.RUTA_FONDO)));

		// Si no le damos un color por defecto a los composites que cuelguen de
		// la shell, seran traslucidos
		shell.setBackgroundMode(SWT.INHERIT_FORCE);

		ventanaPrincipal.getContent(shell);

		// Centrar la ventana
		shellHelper.centrarVentana(display, shell);

		shell.open();

		// con esto hacemos que la shell no se desvanezca en tiempo de ejecucion
		while (!shell.isDisposed()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}

		}
		display.dispose();
	}

	@Override
	public void run() {
		main(null);
	}

}
