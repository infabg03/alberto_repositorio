package com.alberto.boedo.vista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.alberto.boedo.componentes.BotonImagen;
import com.alberto.boedo.controlador.GestorEventos;
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.filtros.CambiarColores;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.FunctionsHelper;
import com.alberto.boedo.helpers.ImageResizeHelper;
import com.alberto.boedo.helpers.MessageDialogHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaCambioColores implements Runnable {

	private final String rutaImagen = i18Message.RUTA_OPEN;
	private final String rutaImagenBack = i18Message.RUTA_BACK;

	private final static int ORIGINAL = 1;
	private final static int CIE = 2;
	private final static int GRAY = 3;
	private final static int HLS = 4;
	private final static int LUV = 5;
	private final static int HSV = 6;

	private static String selected;
	private static Label labelFoto;
	String passwd;
	private static Shell shell;

	private String ruta;
	private Text textoFotoGuardar;

	private FunctionsHelper funciones = BeansFactory.getBean(FunctionsHelper.class);
	private GestorEventos gestor = BeansFactory.getBean(GestorEventos.class);
	private ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);
	private final static Logger log = Logger.getLogger(VentanaCambioColores.class);
	private ImageResizeHelper resizeHelper = BeansFactory.getBean(ImageResizeHelper.class);

	public VentanaCambioColores(String passwd) {
		super();
		this.passwd = passwd;
	}

	public void funcionSetearFotos() {

		Image foto = new Image(Display.getCurrent(), selected);
		foto = resizeHelper.resize(foto, 580, 420);
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

	public void funcionGuardar(ToolItem itemSave) {
		itemSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (!(textoFotoGuardar.getText().length() == 0)) {
						BufferedImage imagen = ImageIO.read(new File(ruta));
						StringBuilder rutaSave = new StringBuilder();
						StringBuilder rutaSaveDirectory = new StringBuilder();
						rutaSaveDirectory.append("C:\\fotos\\").append(passwd);
						rutaSave.append("C:\\fotos\\").append(passwd).append("\\").append(textoFotoGuardar.getText())
								.append(".jpg");
						File directorio = new File(rutaSaveDirectory.toString());
						directorio.mkdirs();
						System.out.println("Se ha creado directorio " + rutaSaveDirectory.toString());
						File file = new File(rutaSave.toString());
						ImageIO.write(imagen, "jpg", file);
						gestor.insertarFoto(passwd, rutaSave.toString());
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					log.warn(e1.getMessage());
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
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

		final ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);
		separator.setWidth(20);

		// Campo de texto para introducir donde queremos guardar la foto
		ToolItem sep2 = new ToolItem(toolBar, SWT.SEPARATOR);
		textoFotoGuardar = new Text(toolBar, SWT.NONE);
		sep2.setWidth(200);
		sep2.setControl(textoFotoGuardar);

		// Zona donde creamos el boton guardar y su funcionalidad
		ToolItem itemSave = new ToolItem(toolBar, SWT.PUSH);
		Image imagenSave = new Image(Display.getCurrent(), i18Message.RUTA_SAVE);
		itemSave.setToolTipText(i18Message.SAVE);
		itemSave.setText(i18Message.SAVE);
		itemSave.setImage(imagenSave);

		funcionGuardar(itemSave);

		final ToolItem separator4 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator4.setWidth(300);

		funcionAbrir(itemPush, parent);

		// Anhadimos el icono para volver atras
		ToolItem itemBack = new ToolItem(toolBar, SWT.PUSH);
		Image imagenBack = new Image(Display.getCurrent(), rutaImagenBack);
		itemBack.setToolTipText(i18Message.BACK);
		itemBack.setText(i18Message.BACK);
		itemBack.setImage(imagenBack);

		funciones.funcionAtras(shell, itemBack, passwd);

	}

	public SelectionListener listenerBotones(final int opcion) {
		return new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (selected != null) {
					ruta = CambiarColores.conversor(selected, opcion);
					Image foto = new Image(Display.getCurrent(), ruta);
					foto = resizeHelper.resize(foto, 580, 420);
					labelFoto.setImage(foto);
				} else {
					MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_FOTO, i18Message.MSG_FOTO);
					log.warn(i18Message.MSG_FOTO_LOG);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		};

	}

	public void addListenersBotonesEstilos(Button bOriginal, Button bCie, Button bGray, Button bHls, Button bLuv,
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

		shellHelper.noCerrarShell(shell);

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
		im1 = resizeHelper.resize(im1, 580, 420);
		labelFoto.setImage(im1);

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
