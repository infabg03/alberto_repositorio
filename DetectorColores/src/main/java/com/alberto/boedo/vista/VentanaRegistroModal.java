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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.componentes.BotonTexto;
import com.alberto.boedo.componentes.LabeledEditText;
import com.alberto.boedo.controlador.GestorEventos;
import com.alberto.boedo.helpers.MessageDialogHelper;
import com.alberto.boedo.helpers.ValidarCamposHelper;
import com.alberto.boedo.helpers.WindowCenterHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaRegistroModal extends Dialog {
	private Display display;
	private Shell shell;
	private final String rutaImagenSalir = i18Message.RUTA_BTN_VOLVER;
	boolean edicion = false;
	String passwd;

	ApplicationContext context = new ClassPathXmlApplicationContext("com/alberto/boedo/xml/beans.xml");
	GestorEventos gestor = context.getBean(GestorEventos.class);

	public VentanaRegistroModal(Shell parent, boolean edicion) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		display = parent.getDisplay();
		createContents();
		this.edicion = edicion;
	}

	public VentanaRegistroModal(Shell parent, boolean edicion, String passwd) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		display = parent.getDisplay();
		this.edicion = edicion;
		this.passwd = passwd;
		createContents();

	}

	public void registroOk(int opcion) {

		if (opcion == 1)
			MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_USER, i18Message.MSG_OK_USER);
		else
			MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_USER, i18Message.MSG_BAD_USER);
	}

	public void open() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContents() {
		shell = new Shell(getParent(), getStyle());

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
			}
		});

		shell.setSize(418, 600);

		WindowCenterHelper.centrarVentana(display, shell);

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		shell.setLayout(gridLayout);

		Composite compo1 = new Composite(shell, SWT.NONE);
		GridLayout gridLayout2 = new GridLayout(2, true);
		gridLayout2.marginWidth = 5;
		gridLayout2.marginHeight = 5;
		gridLayout2.verticalSpacing = 0;
		gridLayout2.horizontalSpacing = 0;
		compo1.setLayout(gridLayout2);

		final LabeledEditText nombreTxt = new LabeledEditText(compo1, SWT.LEFT, "", i18Message.NOMBRE, false, 45);

		Button btnImgSalir = new BotonImagen().getBotonImagen(display, compo1, rutaImagenSalir);
		GridData gridData = new GridData(SWT.CENTER, SWT.FILL, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		btnImgSalir.setLayoutData(gridData);
		btnImgSalir.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		final LabeledEditText apellidosTxt = new LabeledEditText(shell, SWT.NONE, "", i18Message.APELLIDOS, false, 50);
		final LabeledEditText emailTxt = new LabeledEditText(shell, SWT.NONE, "", i18Message.MAIL, false, 50);
		final LabeledEditText passwdTxt = new LabeledEditText(shell, SWT.NONE, "", i18Message.PASSWD, true, 50);
		final LabeledEditText telefonoTxt = new LabeledEditText(shell, SWT.NONE, "", i18Message.TLFNO, false, 50);

		Button botonRegistro = new BotonTexto().devuelveBotonTexto(shell, SWT.NONE, i18Message.CONFIRM);

		List<String> lista = new ArrayList<String>();
		lista.add(nombreTxt.getText());
		lista.add(apellidosTxt.getText());
		lista.add(emailTxt.getText());
		lista.add(passwdTxt.getText());
		lista.add(telefonoTxt.getText());

		botonRegistro.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, true, true));

		// FIXME: al registrarse con un password ya existente debe dar error y
		// advertir
		botonRegistro.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (camposCorrectos(passwdTxt.getText(), emailTxt.getText(), telefonoTxt.getText())) {

					if (!edicion) {
						List<String> lista = new ArrayList<String>();
						lista.add(nombreTxt.getText());
						lista.add(apellidosTxt.getText());
						lista.add(emailTxt.getText());
						lista.add(passwdTxt.getText());
						lista.add(telefonoTxt.getText());
						gestor.insertaUsuario(lista);
						registroOk(1);
						shell.dispose();

					} else {
						List<String> lista = new ArrayList<String>();
						lista.add(nombreTxt.getText());
						lista.add(apellidosTxt.getText());
						lista.add(emailTxt.getText());
						lista.add(passwdTxt.getText());
						lista.add(telefonoTxt.getText());
						gestor.modificoUsuario(lista, passwd);
						registroOk(2);
						shell.dispose();
					}
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private boolean camposCorrectos(String passwd, String mail, String tlfn) {
		if (!ValidarCamposHelper.longitudPasswd(passwd)) {
			MessageDialogHelper.aceptarDialog(shell, i18Message.FORMAT_ERROR, i18Message.MSG_ERROR_LONG_PASSWD);
			return false;
		} else if (!ValidarCamposHelper.mayuscMinuscPasswd(passwd)) {
			MessageDialogHelper.aceptarDialog(shell, i18Message.FORMAT_ERROR, i18Message.MSG_ERROR_PASSWD);
			return false;
		} else if (!ValidarCamposHelper.formatoTelefono(tlfn)) {
			MessageDialogHelper.aceptarDialog(shell, i18Message.FORMAT_ERROR, i18Message.MSG_ERROR_TELEFONO);
			return false;
		} else if (!ValidarCamposHelper.formatoEmail(mail)) {
			MessageDialogHelper.aceptarDialog(shell, i18Message.FORMAT_ERROR, i18Message.MSG_ERROR_MAIL);
			return false;
		} else
			return true;
	}
}
