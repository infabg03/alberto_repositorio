package com.alberto.boedo.ventanas;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.componentes.BotonTexto;
import com.alberto.boedo.componentes.LabeledEditText;
import com.alberto.boedo.controlador.GestorEventos;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.MessageDialogHelper;
import com.alberto.boedo.helpers.WindowCenterHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaPrincipal implements Runnable {

	private Button buttonSever;
	private Button buttonMock;
	private Button btnInfo;

	ApplicationContext context = new ClassPathXmlApplicationContext("com/alberto/boedo/xml/beans.xml");
	private GestorEventos gestor = context.getBean(GestorEventos.class);

	private void getContent(final Shell shell) {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(ColorHelper.COLOR_WHITE);
		composite.setLayout(new GridLayout(2, true));
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		composite.setLayoutData(gridData);

		final LabeledEditText login = new LabeledEditText(composite, SWT.NONE, "", i18Message.lOGIN, false, 45);

		Composite radioGroup = new Composite(composite, SWT.NONE);
		radioGroup.setLayout(new GridLayout(3, true));

		final LabeledEditText passwd = new LabeledEditText(shell, SWT.NONE, "", i18Message.PASSWD, true, 50);

		Button registro = new BotonTexto().devuelveBotonTexto(shell, SWT.NONE, i18Message.lOGIN);
		registro.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (buttonSever.getSelection()) {
					if (gestor.correctLogin(login.getText(), passwd.getText())) {
						Thread tVentanaSelectora = new Thread(new VentanaSelectora(login.getText()));
						Display.getCurrent().dispose();
						tVentanaSelectora.run();
					} else {
						MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_ACCESO, i18Message.MSG_ACCESO);

						login.setText("");
						passwd.setText("");
					}
				} else if (buttonMock.getSelection()) {
					if (login.getText().matches(i18Message.MOCK_MAIL)
							&& passwd.getText().matches(i18Message.MOCK_PASSWD)) {
						Thread tVentanaSelectora = new Thread(new VentanaSelectora(false));
						Display.getCurrent().dispose();
						tVentanaSelectora.run();
					} else {
						MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_ACCESO, i18Message.MOCK_ACCES_ERROR);
						VentanaPrincipal.this.btnInfo.forceFocus();
						login.setText("");
						passwd.setText("");
					}
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

		buttonSever = new Button(radioGroup, SWT.RADIO);
		buttonSever.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		buttonSever.setText(i18Message.SERVER);
		buttonSever.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				link.setEnabled(true);
			}
		});

		buttonSever.setSelection(true);

		buttonMock = new Button(radioGroup, SWT.RADIO);
		buttonMock.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		buttonMock.setText(i18Message.MOCK);
		buttonMock.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				link.setEnabled(false);
			}
		});

		VentanaPrincipal.this.btnInfo = new BotonImagen().getBotonImagen(Display.getCurrent(), radioGroup,
				i18Message.RUTA_INFO);
		VentanaPrincipal.this.btnInfo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_ACCESO, i18Message.MSG_MOCK);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public static void main(String[] args) {
		final Display display = new Display();
		final VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		final Shell shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(500, 300);
		shell.setBackgroundImage(new Image(display, new ImageData(i18Message.RUTA_FONDO)));

		ventanaPrincipal.getContent(shell);

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
