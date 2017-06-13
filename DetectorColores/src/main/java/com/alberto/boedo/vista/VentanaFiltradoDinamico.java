package com.alberto.boedo.vista;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.componentes.LabeledCombo;
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.filtros.FiltradoDinamico;
import com.alberto.boedo.filtros.IFiltradoDinamico;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaFiltradoDinamico implements Runnable {

	private Shell shell;
	private ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);
	private String passwd;
	private IFiltradoDinamico filtradoDinamico = BeansFactory.getBean(FiltradoDinamico.class);

	public VentanaFiltradoDinamico() {
		super();
	}

	public VentanaFiltradoDinamico(String passwd) {
		super();
		this.passwd = passwd;
	}

	private void listenersBotones(Button botonPlay, Button botonBack) {
		botonBack.addSelectionListener(new SelectionListener() {

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

		botonPlay.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				filtradoDinamico.execute();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void getContent() {
		LabeledCombo comboColores = new LabeledCombo(shell, i18Message.MSG_SELEC_COLOR, SWT.DROP_DOWN);
		comboColores.add(i18Message.COLOR_AMARILLO_NARANJA);
		comboColores.add(i18Message.COLOR_AZUL);
		comboColores.add(i18Message.COLOR_ROJO);
		comboColores.add(i18Message.COLOR_VERDE);
		comboColores.add(i18Message.COLOR_VIOLETA);

		Button botonPlay = new BotonImagen().getBotonImagen(Display.getCurrent(), shell, i18Message.RUTA_PLAY);
		Button botonBack = new BotonImagen().getBotonImagen(Display.getCurrent(), shell, i18Message.RUTA_BTN_EXIT);

		listenersBotones(botonPlay, botonBack);

	}

	@Override
	public void run() {
		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		GridLayout layout = new GridLayout(3, false);
		shell.setLayout(layout);
		shell.setSize(500, 200);
		shell.setBackground(ColorHelper.COLOR_BLACK);

		getContent();

		shellHelper.noCerrarShell(shell);

		// Centrar la ventana
		shellHelper.centrarVentana(display, shell);

		shell.open();

		// Con esto hacemos que la shell no se desvanezca en tiempo de ejecucion
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}

}
