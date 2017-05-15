package Ventanas;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Componentes.BotonImagen;
import Filtros.CambiarColores;
import Helpers.ColorHelper;
import Helpers.GoBackHelper;
import Helpers.ImageResizeHelper;
import Helpers.MessageDialogHelper;
import Helpers.WindowCenterHelper;
import naming.i18Message;

public class VentanaCambioColores implements Runnable {

	private final String rutaImagen = i18Message.RUTA_OPEN;
	private final String rutaImagenBack = i18Message.RUTA_BACK;

	private final static int ORIGINAL = 1;
	private final static int CIE = 2;
	private final static int GRAY = 3;
	private final static int HLS = 4;
	private final static int LUV = 5;
	private final static int HSV = 6;

	static String selected;
	static Label labelFoto;
	String passwd;
	static Shell shell;

	public VentanaCambioColores(String passwd) {
		super();
		this.passwd = passwd;
	}

	public void funcionSetearFotos() {

		Image foto = new Image(Display.getCurrent(), selected);
		foto = ImageResizeHelper.resize(foto, 580, 420);
		labelFoto.setImage(foto);

	}

	public void funcionAbrir(ToolItem itemPush, final Composite parent) {

		itemPush.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// Guardamos en selected la ruta a la imagen seleccionada
				FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);
				fd.setText(i18Message.OPEN);
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.jpg", "*.png" };
				fd.setFilterExtensions(filterExt);
				selected = fd.open();

				funcionSetearFotos();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void createToolBar(final Composite parent) {

		// Creamos la toolbar
		ToolBar toolBar = new ToolBar(parent, SWT.WRAP);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		toolBar.setBackground(ColorHelper.COLOR_AQUAMARINE);

		// Anhadimos al toolbar el iconito para ejecutar un filechooser
		ToolItem itemPush = new ToolItem(toolBar, SWT.PUSH);
		Image imagen = new Image(Display.getCurrent(), rutaImagen);
		itemPush.setToolTipText(i18Message.OPEN);
		itemPush.setText(i18Message.OPEN);
		itemPush.setImage(imagen);

		funcionAbrir(itemPush, parent);

		ToolItem itemBack = new ToolItem(toolBar, SWT.PUSH);
		Image imagenBack = new Image(Display.getCurrent(), rutaImagenBack);
		itemBack.setToolTipText(i18Message.BACK);
		itemBack.setText(i18Message.BACK);
		itemBack.setImage(imagenBack);

		GoBackHelper.funcionAtras(shell, itemBack, passwd);

	}

	public static SelectionListener listenerBotones(final int opcion) {
		return new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (selected != null) {
					Image foto = new Image(Display.getCurrent(), CambiarColores.conversor(selected, opcion));
					foto = ImageResizeHelper.resize(foto, 580, 420);
					labelFoto.setImage(foto);
				} else {
					MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_FOTO, i18Message.MSG_FOTO);
					MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		};

	}

	public static void addListenersBotonesEstilos(Button bOriginal, Button bCie, Button bGray, Button bHls, Button bLuv,
			Button bHsv) {

		bOriginal.addSelectionListener(listenerBotones(ORIGINAL));
		bCie.addSelectionListener(listenerBotones(CIE));
		bGray.addSelectionListener(listenerBotones(GRAY));
		bHls.addSelectionListener(listenerBotones(HLS));
		bLuv.addSelectionListener(listenerBotones(LUV));
		bHsv.addSelectionListener(listenerBotones(HSV));
	}

	public void createBotonesEsilo(Composite parent) {

		Button b1 = new BotonImagen().getBotonImagen(Display.getCurrent(), parent, i18Message.RUTA_BTN_ORIGINAL);

		Button b2 = new BotonImagen().getBotonImagen(Display.getCurrent(), parent, i18Message.RUTA_BTN_CIE);

		Button b3 = new BotonImagen().getBotonImagen(Display.getCurrent(), parent, i18Message.RUTA_BTN_GRAY);

		Button b4 = new BotonImagen().getBotonImagen(Display.getCurrent(), parent, i18Message.RUTA_BTN_HLS);

		Button b5 = new BotonImagen().getBotonImagen(Display.getCurrent(), parent, i18Message.RUTA_BTN_LUV);

		Button b6 = new BotonImagen().getBotonImagen(Display.getCurrent(), parent, i18Message.RUTA_BTN_HSV);

		addListenersBotonesEstilos(b1, b2, b3, b4, b5, b6);
	}

	@Override
	public void run() {

		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(700, 500);
		shell.setBackground(ColorHelper.COLOR_BLACK);

		// Deshabilita boton cerrado de la shell
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
			}
		});

		createToolBar(shell);

		Composite compositeFotos = new Composite(shell, SWT.NONE);
		compositeFotos.setBackground(ColorHelper.COLOR_BLACK);
		compositeFotos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compositeFotos.setLayout(new GridLayout(2, false));

		Composite zonaIconos = new Composite(compositeFotos, SWT.NONE);
		zonaIconos.setBackground(ColorHelper.COLOR_BLACK);
		GridLayout gridLayoutIconos = new GridLayout(1, false);
		zonaIconos.setLayout(gridLayoutIconos);
		zonaIconos.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));

		createBotonesEsilo(zonaIconos);

		Composite compositeImage = new Composite(compositeFotos, SWT.NONE);
		compositeImage.setBackground(ColorHelper.COLOR_BLACK);
		compositeImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compositeImage.setLayout(new GridLayout(1, false));

		labelFoto = new Label(compositeImage, SWT.NONE);

		Image im1 = new Image(display, i18Message.RUTA_BLANCO);
		im1 = ImageResizeHelper.resize(im1, 580, 420);
		labelFoto.setImage(im1);

		// Centrar la ventana
		WindowCenterHelper.centrarVentana(display, shell);

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
