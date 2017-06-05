package com.alberto.boedo.vista;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.controlador.GestorEventos;
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.filtros.FiltradoDinamico;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaSelectora implements Runnable {
	private Shell shell;
	private Display display;
	private String passwd;
	private boolean pintoEditar;
	private GestorEventos gestor = BeansFactory.getBean(GestorEventos.class);
	private ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);

	public VentanaSelectora(String passwd) {
		super();
		this.passwd = passwd;
	}

	public void ponerVisibles(List<Button> arrayBtn, boolean visible) {
		for (Button boton : arrayBtn) {
			boton.setVisible(visible);
		}
	}

	public void ejecutarListerners(Button editar, Button salir, Button estatico, Button dinamico, Button cambioColor,
			Button moveMouse, Button galeria) {
		salir.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Thread t = new Thread(new VentanaPrincipal());
				t.start();
				shell.dispose();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		editar.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				VentanaRegistroModal secondWindow = new VentanaRegistroModal(VentanaSelectora.this.shell, true, passwd);
				secondWindow.open();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		estatico.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
				Thread tFiltradoEstatico = new Thread(new VentanaFiltradoEstatico(passwd));
				tFiltradoEstatico.start();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		dinamico.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FiltradoDinamico.execute();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		cambioColor.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
				Thread tCambioColor = new Thread(new VentanaCambioColores(passwd));
				tCambioColor.start();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		galeria.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				Thread tVentanaGaleria = new Thread(new VentanaGaleria(passwd));
				tVentanaGaleria.start();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void getContent(Composite shell) {
		Composite composite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		composite.setBackground(ColorHelper.COLOR_BLACK);

		final Button btnEditar = new BotonImagen().getBotonImagen(display, composite, i18Message.RUTA_BTN_EDITAR);
		btnEditar.setToolTipText(i18Message.EDITAR);
		final Button btnOpciones = new BotonImagen().getBotonImagen(display, composite, i18Message.RUTA_BTN_OPCIONES);
		btnOpciones.setToolTipText(i18Message.OPCIONES);
		final Button btnSalir = new BotonImagen().getBotonImagen(display, composite, i18Message.RUTA_BTN_EXIT);
		btnSalir.setToolTipText(i18Message.SALIR);

		Composite compositeSup = new Composite(shell, SWT.NONE);
		GridLayout gridLayout2 = new GridLayout(5, false);
		gridLayout2.marginTop = 20;
		compositeSup.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		compositeSup.setLayout(gridLayout2);
		compositeSup.setBackground(ColorHelper.COLOR_BLACK);

		final List<Button> listaBotones = new ArrayList<Button>();

		final Button btnFiltradoEstatico = new BotonImagen().getBotonImagen(display, compositeSup,
				i18Message.RUTA_BTN_FILTRADO_ESTATIC0);
		btnFiltradoEstatico.setToolTipText(i18Message.FILTRADO_ESTATICO);

		listaBotones.add(btnFiltradoEstatico);
		final Button btnFiltradoDinamico = new BotonImagen().getBotonImagen(display, compositeSup,
				i18Message.RUTA_BTN_FILTRADO_DINAMICO);
		btnFiltradoDinamico.setToolTipText(i18Message.FILTRADO_DINAMICO);
		listaBotones.add(btnFiltradoDinamico);
		final Button btnCambioColor = new BotonImagen().getBotonImagen(display, compositeSup,
				i18Message.RUTA_BTN_CAMBIO_COLOR);
		btnCambioColor.setToolTipText(i18Message.CAMBIO_COLOR);
		listaBotones.add(btnCambioColor);
		final Button btnMoverRaton = new BotonImagen().getBotonImagen(display, compositeSup,
				i18Message.RUTA_BTN_MOVER_RATON);
		listaBotones.add(btnMoverRaton);
		btnMoverRaton.setToolTipText(i18Message.MOVER_RATON);
		final Button btnGaleria = new BotonImagen().getBotonImagen(display, compositeSup, i18Message.RUTA_BTN_GALERIA);
		listaBotones.add(btnGaleria);
		btnGaleria.setToolTipText(i18Message.GALERIA);

		if (!gestor.tieneFotos(passwd)) {
			btnGaleria.setEnabled(false);
		}

		ponerVisibles(listaBotones, false);

		btnOpciones.addListener(SWT.MouseHover, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				ponerVisibles(listaBotones, true);
				btnOpciones.forceFocus();
			}
		});

		btnEditar.addListener(SWT.MouseHover, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				ponerVisibles(listaBotones, false);

			}
		});

		btnSalir.addListener(SWT.MouseHover, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				ponerVisibles(listaBotones, false);

			}
		});

		// Seccion listeners
		ejecutarListerners(btnEditar, btnSalir, btnFiltradoEstatico, btnFiltradoDinamico, btnCambioColor, btnMoverRaton,
				btnGaleria);
	}

	@Override
	public void run() {
		display = new Display();

		// Hace que la shell sea de tamano fijo
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(425, 300);
		shell.setBackground(ColorHelper.COLOR_BLACK);

		// Deshabilita boton cerrado de la shell
		shellHelper.noCerrarShell(shell);

		getContent(shell);

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
