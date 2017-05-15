package Ventanas;

import javax.swing.ButtonModel;

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

import Componentes.BotonImagen;
import Componentes.BotonTexto;
import Componentes.LabeledEditText;
import Helpers.ColorHelper;
import Helpers.MessageDialogHelper;
import Helpers.WindowCenterHelper;
import controlador.GestorEventos;

public class VentanaPrincipal implements Runnable {

	static Shell shell;
	static Button buttonSever;
	static Button buttonMock;
	private static String rutaInfo = "src/resources/info.png";

	private static void getContent(final Shell shell) {
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(ColorHelper.COLOR_WHITE);
		composite.setLayout(new GridLayout(2, true));
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		composite.setLayoutData(gridData);

		final LabeledEditText login = new LabeledEditText(composite, SWT.NONE, "", "Login", false, 45);

		Composite radioGroup = new Composite(composite, SWT.NONE);
		radioGroup.setLayout(new GridLayout(3, true));

		final LabeledEditText passwd = new LabeledEditText(shell, SWT.NONE, "", "Password", true, 50);

		Button registro = new BotonTexto().devuelveBotonTexto(shell, SWT.NONE, "Login");
		registro.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (buttonSever.getSelection()) {
					if (GestorEventos.correctLogin(login.getText(), passwd.getText())) {
						Thread tVentanaSelectora = new Thread(new VentanaSelectora(login.getText()));
						Display.getCurrent().dispose();
						tVentanaSelectora.run();
					} else {
						MessageDialogHelper.aceptarDialog(shell, "Informacion de acceso",
								"El login o password son incorrectos");

						login.setText("");
						passwd.setText("");
					}
				} else if (buttonMock.getSelection()) {
					if (login.getText().matches("mock@email.com") && passwd.getText().matches("12345678")) {
						Thread tVentanaSelectora = new Thread(new VentanaSelectora(false));
						Display.getCurrent().dispose();
						tVentanaSelectora.run();
					}
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		final Link link = new Link(shell, SWT.NONE);
		link.setText("¿No estas registrado? <A>Registrate ahora</A>");
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						VentanaRegistroModal secondWindow = new VentanaRegistroModal(VentanaPrincipal.this.shell,
								false);
						secondWindow.open();

					}
				});
			}
		});

		buttonSever = new Button(radioGroup, SWT.RADIO);
		buttonSever.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		buttonSever.setText("Server");
		buttonSever.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				link.setEnabled(true);
			}
		});

		buttonSever.setSelection(true);

		buttonMock = new Button(radioGroup, SWT.RADIO);
		buttonMock.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		buttonMock.setText("Mock");
		buttonMock.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				link.setEnabled(false);
			}
		});

		Button btnInfo = new BotonImagen().getBotonImagen(Display.getCurrent(), radioGroup, rutaInfo);
		btnInfo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialogHelper.aceptarDialog(shell, "Informacion registro",
						"Configuracion Mock:\nUsuario: mock@email.com\nPassword: 12345678");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public static void main(String[] args) {
		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(500, 300);
		shell.setBackgroundImage(new Image(display, new ImageData("src/resources/fondoApp.jpg")));

		getContent(shell);

		// Centrar la ventana
		WindowCenterHelper.centrarVentana(display, shell);

		shell.open();

		// con esto hacemos que la shell no se desvanezca en tiempo de ejecucion
		while (!shell.isDisposed()) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Exception e) {

			}

		}
		display.dispose();
	}

	@Override
	public void run() {
		main(null);
	}

}
