package com.alberto.boedo.vista;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.helpers.MessageDialogHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.helpers.ValidarCamposHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaRegistroModal extends Dialog {
	private Display display;
	private Shell shell;
	private final String rutaImagenSalir = i18Message.RUTA_BTN_VOLVER;
	boolean edicion = false;
	String passwd;

	ApplicationContext context = new ClassPathXmlApplicationContext("com/alberto/boedo/xml/beans.xml");
	GestorEventos gestor = context.getBean(GestorEventos.class);
	private ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);

	/**
	 * Crea una nueva ventana de registro modal.
	 * 
	 * @param parent
	 *            Composite padre de la ventana.
	 * @param edicion
	 *            Si esta abierta en modo edicion o no.
	 */
	public VentanaRegistroModal(Shell parent, boolean edicion) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		display = parent.getDisplay();
		createContents();
		this.edicion = edicion;
	}

	/**
	 * Crea una nueva ventana de registro modal.
	 * 
	 * @param parent
	 *            Composite padre de la ventana.
	 * @param edicion
	 *            Si esta abierta en modo edicion o no.
	 * @param passwd
	 *            Email del usuario.
	 */
	public VentanaRegistroModal(Shell parent, boolean edicion, String passwd) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		display = parent.getDisplay();
		this.edicion = edicion;
		this.passwd = passwd;
		createContents();

	}

	/**
	 * Crea un mensaje de aviso referente a la correcta o no ejecucion del
	 * registro.
	 * 
	 * @param opcion
	 *            Opcion que controla que mensaje mostar.
	 */
	public void registroOk(int opcion) {

		if (opcion == 1)
			MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_USER, i18Message.MSG_OK_USER);
		else
			MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_USER, i18Message.MSG_MOD_USER);
	}

	/**
	 * Funcion que abre la ventana.
	 * 
	 */
	public void open() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Funcion que crea los contenidos de la ventana.
	 * 
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());

		shellHelper.noCerrarShell(shell);

		shell.setSize(418, 600);

		shellHelper.centrarVentana(display, shell);

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

		if (edicion) {
			emailTxt.setDisable();
		}

		Button botonRegistro = new BotonTexto().devuelveBotonTexto(shell, SWT.NONE, i18Message.CONFIRM);

		List<String> lista = new ArrayList<String>();
		lista.add(nombreTxt.getTexto());
		lista.add(apellidosTxt.getTexto());
		lista.add(emailTxt.getTexto());
		lista.add(passwdTxt.getTexto());
		lista.add(telefonoTxt.getTexto());

		botonRegistro.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, true, true));

		botonRegistro.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				String contra = "";
				contra = edicion == true ? passwd : emailTxt.getTexto();

				if (camposCorrectos(passwdTxt.getTexto(), contra, telefonoTxt.getTexto())) {

					if (!edicion) {
						if (!gestor.usuarioExiste(emailTxt.getTexto())) {
							List<String> lista = new ArrayList<String>();
							lista.add(nombreTxt.getTexto());
							lista.add(apellidosTxt.getTexto());
							lista.add(emailTxt.getTexto());
							lista.add(passwdTxt.getTexto());
							lista.add(telefonoTxt.getTexto());
							gestor.insertaUsuario(lista);
							registroOk(1);
							shell.dispose();
						} else {
							MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_USER, i18Message.MSG_BAD_USER);
						}

					} else {
						List<String> lista = new ArrayList<String>();
						lista.add(nombreTxt.getTexto());
						lista.add(apellidosTxt.getTexto());
						lista.add(contra);
						lista.add(passwdTxt.getTexto());
						lista.add(telefonoTxt.getTexto());
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

	/**
	 * Funcion que comprueba el correcto contenido de los campos.
	 * 
	 * @param passwd
	 *            Password introducido.
	 * @param mail
	 *            Email introducido.
	 * @param tlfn
	 *            Telefono introducido.
	 * @return Valor booleano correspondiente a la correcion de formato de los
	 *         campos comprobados.
	 */
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
