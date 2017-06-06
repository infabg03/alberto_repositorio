package com.alberto.boedo.vista;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.FunctionsHelper;
import com.alberto.boedo.helpers.ImageResizeHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.modelo.Foto;
import com.alberto.boedo.modelo.Persona;
import com.alberto.boedo.modelo.PersonaDAO;
import com.alberto.boedo.naming.i18Message;

public class VentanaGaleria implements Runnable {
	static List<Foto> listaFotos;
	private String passwd;
	private Image imagen;
	PersonaDAO personaDAO = BeansFactory.getBean(PersonaDAO.class);
	int i = 1;
	private FunctionsHelper funciones = BeansFactory.getBean(FunctionsHelper.class);
	private Shell shell;
	private Label label;
	private ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);
	private ImageResizeHelper resizeHelper = BeansFactory.getBean(ImageResizeHelper.class);

	/**
	 * Crea una nueva ventana galeria.
	 * 
	 * @param passwd
	 *            Email del usuario.
	 */
	public VentanaGaleria(String passwd) {
		super();
		this.passwd = passwd;
	}

	/**
	 * Listener asociado al boton de retrasar foto.
	 * 
	 * @param btnLeft
	 *            Boton izquierda.
	 */
	public void lefListener(Button btnLeft) {
		btnLeft.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (i > 1) {
					i--;
					imagen = new Image(Display.getCurrent(), listaFotos.get(i).getRuta());
					imagen = resizeHelper.resize(imagen, 770, 500);
					label.setImage(imagen);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Listener asociado al boton de avanzar foto.
	 * 
	 * @param btnRight
	 *            Boton derecha.
	 */
	public void rightListener(Button btnRight) {
		btnRight.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (i < listaFotos.size() - 1) {
					i++;
					imagen = new Image(Display.getCurrent(), listaFotos.get(i).getRuta());
					imagen = resizeHelper.resize(imagen, 770, 500);
					label.setImage(imagen);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Crea el contenido de la ventana.
	 * 
	 */
	public void getContent() {
		Persona p = personaDAO.getPersona(passwd);
		listaFotos = p.getFotos();

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		composite.setLayoutData(gridData);
		composite.setBackground(ColorHelper.COLOR_DARK_GRAY);

		// Creamos la toolbar
		ToolBar toolBar = new ToolBar(composite, SWT.WRAP);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolBar.setBackground(ColorHelper.COLOR_DARK_GRAY);

		final ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);
		separator.setWidth(730);

		ToolItem itemBack = new ToolItem(toolBar, SWT.PUSH);
		Image imagenBack = new Image(Display.getCurrent(), i18Message.RUTA_BACK16);
		itemBack.setToolTipText(i18Message.BACK);
		itemBack.setText(i18Message.BACK);
		itemBack.setImage(imagenBack);

		funciones.funcionAtras(shell, itemBack, passwd);

		// Label donde se insertaran las fotos
		label = new Label(composite, SWT.BORDER);
		imagen = new Image(Display.getCurrent(), listaFotos.get(i).getRuta());
		imagen = resizeHelper.resize(imagen, 770, 500);
		label.setImage(imagen);

		final Composite botonera = new Composite(composite, SWT.NONE);
		GridLayout gridLayoutBotonera = new GridLayout(2, false);
		gridLayoutBotonera.marginWidth = 5;
		gridLayoutBotonera.marginHeight = 5;
		gridLayoutBotonera.verticalSpacing = 0;
		gridLayoutBotonera.horizontalSpacing = 0;
		botonera.setLayout(gridLayoutBotonera);
		botonera.setBackground(ColorHelper.COLOR_DARK_GRAY);

		GridData gridDataBotonera = new GridData(SWT.CENTER, SWT.FILL, true, false);
		gridDataBotonera.widthHint = SWT.DEFAULT;
		gridDataBotonera.heightHint = SWT.DEFAULT;
		botonera.setLayoutData(gridDataBotonera);

		final Button btnLeft = new BotonImagen().getBotonImagen(Display.getCurrent(), botonera, i18Message.RUTA_LEFT);
		lefListener(btnLeft);
		final Button btnRight = new BotonImagen().getBotonImagen(Display.getCurrent(), botonera, i18Message.RUTA_RIGHT);
		rightListener(btnRight);
	}

	@Override
	public void run() {
		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(800, 640);
		shell.setBackground(ColorHelper.COLOR_DARK_GRAY);

		getContent();

		// Deshabilita boton cerrado de la shell
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
