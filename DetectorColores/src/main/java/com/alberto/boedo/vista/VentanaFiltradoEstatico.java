package com.alberto.boedo.vista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.alberto.boedo.controlador.GestorEventos;
import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.filtros.FiltradoEstatico;
import com.alberto.boedo.filtros.IfiltradoEstatico;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.FunctionsHelper;
import com.alberto.boedo.helpers.ImageResizeHelper;
import com.alberto.boedo.helpers.MessageDialogHelper;
import com.alberto.boedo.helpers.ShellHelper;
import com.alberto.boedo.naming.i18Message;

public class VentanaFiltradoEstatico implements Runnable {

	private List<String> listaFotos;

	private Label labelFoto;
	private Label labelFoto2;
	private Combo combo;
	private String selected;
	private Shell shell;
	private String passwd;
	private Text textoFotoGuardar;

	private IfiltradoEstatico filtradoEstatico = BeansFactory.getBean(FiltradoEstatico.class);
	private GestorEventos gestor = BeansFactory.getBean(GestorEventos.class);
	private FunctionsHelper funciones = BeansFactory.getBean(FunctionsHelper.class);
	private ShellHelper shellHelper = BeansFactory.getBean(ShellHelper.class);
	private final static Logger log = Logger.getLogger(VentanaFiltradoEstatico.class);
	private ImageResizeHelper resizeHelper = BeansFactory.getBean(ImageResizeHelper.class);

	/**
	 * Setea un objeto filtradoEstatico.
	 * 
	 * @param filtradoEstatico
	 *            El objeto filtrado estatico.
	 */
	public void setFiltradoEstado(IfiltradoEstatico filtradoEstatico) {
		this.filtradoEstatico = filtradoEstatico;
	}

	/**
	 * Crea una nueva ventana filtrado estatico.
	 * 
	 */
	public VentanaFiltradoEstatico() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Crea una nueva ventana filtrado estatico.
	 * 
	 * @param passwd
	 *            Email del usuario.
	 */
	public VentanaFiltradoEstatico(String passwd) {
		super();
		this.passwd = passwd;
	}

	/**
	 * Setea las fotos en las label correspondientes.
	 * 
	 */
	public void funcionSetearFotos() {
		if (selected != null) {
			listaFotos = filtradoEstatico.conversor(selected, combo.getText(), passwd);

			Image original = new Image(Display.getCurrent(), listaFotos.get(0));
			original = resizeHelper.resize(original, 580, 420);
			Image filtrada = new Image(Display.getCurrent(), listaFotos.get(1));
			filtrada = resizeHelper.resize(filtrada, 580, 420);

			labelFoto.setImage(original);
			labelFoto2.setImage(filtrada);
		} else {
			MessageDialogHelper.aceptarDialog(shell, i18Message.INFO_FOTO, i18Message.MSG_FOTO);
			log.warn(i18Message.MSG_FOTO_LOG);
		}
	}

	/**
	 * Listener para el cambio de seleccion en el combo.
	 * 
	 */
	public void funcionCambioCombo() {
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				filtradoEstatico.setValorOriginal();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Listener para el boton de seleccion de fichero.
	 * 
	 * @param itemPush
	 *            Boton de apertura.
	 * @param parent
	 *            Composite padre.
	 */
	public void funcionAbrir(ToolItem itemPush, final Shell parent) {

		itemPush.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// Guardamos en selected la ruta a la imagen seleccionada
				FileDialog fd = new FileDialog(parent, SWT.OPEN);
				fd.setText("Open");
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

	/**
	 * Listener para el boton de recarga.
	 * 
	 * @param itemReload
	 *            Boton de recarga.
	 */
	public void funcionRecargar(ToolItem itemReload) {

		itemReload.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				funcionSetearFotos();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Listener para el boton de aumentar espectro.
	 * 
	 * @param itemPlus
	 *            Boton aumentar espectro.
	 */
	public void funcionAumentar(ToolItem itemPlus) {
		itemPlus.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				filtradoEstatico.disminuirValor();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Listener para el boton de disminuir espectro.
	 * 
	 * @param itemMinus
	 *            Boton de disminuir espectro.
	 */
	public void funcionDisminuir(ToolItem itemMinus) {
		itemMinus.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				filtradoEstatico.aumentarValor();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Listener para el boton de guardado.
	 * 
	 * @param itemSave
	 *            Boton de guardado.
	 */
	public void funcionGuardar(ToolItem itemSave) {
		itemSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (!(textoFotoGuardar.getText().length() == 0)) {
						BufferedImage imagen = ImageIO.read(new File(listaFotos.get(1)));
						StringBuilder rutaSave = new StringBuilder();
						StringBuilder rutaSaveDirectory = new StringBuilder();
						rutaSaveDirectory.append("C:\\fotos\\").append(passwd);
						rutaSave.append("C:\\fotos\\").append(passwd).append("\\").append(textoFotoGuardar.getText())
								.append(".jpg");
						File directorio = new File(rutaSaveDirectory.toString());
						directorio.mkdirs();
						File file = new File(rutaSave.toString());
						ImageIO.write(imagen, "jpg", file);
						gestor.insertarFoto(passwd, rutaSave.toString());
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Funcion que se encarga de crear el toolbar.
	 * 
	 * @param parent
	 *            Composite padre del que colgara el toolbar.
	 */
	public void createToolBar(final Shell parent) {

		// Creamos la toolbar
		ToolBar toolBar = new ToolBar(parent, SWT.WRAP);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		toolBar.setBackground(ColorHelper.COLOR_AQUAMARINE);

		// Anhadimos al toolbar el iconito para ejecutar un filechooser
		ToolItem itemPush = new ToolItem(toolBar, SWT.PUSH);
		Image imagen = new Image(Display.getCurrent(), i18Message.RUTA_OPEN);
		itemPush.setToolTipText(i18Message.OPEN);
		itemPush.setText(i18Message.OPEN);
		itemPush.setImage(imagen);

		final ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);
		separator.setWidth(20);

		// Anhadimos combo selector colores
		ToolItem sep = new ToolItem(toolBar, SWT.SEPARATOR);
		combo = new Combo(toolBar, SWT.READ_ONLY);
		sep.setText(i18Message.COLOR);
		combo.add(i18Message.COLOR_AZUL);
		combo.add(i18Message.COLOR_VERDE);
		combo.add(i18Message.COLOR_ROJO);
		combo.add(i18Message.COLOR_VIOLETA);
		combo.add(i18Message.COLOR_AMARILLO_NARANJA);
		combo.setText(i18Message.COLOR_AZUL);
		combo.pack();
		sep.setWidth(combo.getSize().x);
		sep.setControl(combo);

		final ToolItem separator2 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator2.setWidth(50);

		// Creamos la zona que aumenta el parametro

		ToolItem itemPlus = new ToolItem(toolBar, SWT.PUSH);
		Image imagenPlus = new Image(Display.getCurrent(), i18Message.RUTA_PLUS);
		itemPlus.setToolTipText(i18Message.INCREASE);
		itemPlus.setText(i18Message.MAS_ESPECTRO);
		itemPlus.setImage(imagenPlus);

		ToolItem itemMinus = new ToolItem(toolBar, SWT.PUSH);
		Image imagenMinus = new Image(Display.getCurrent(), i18Message.RUTA_MINUS);
		itemMinus.setToolTipText(i18Message.DECREASE);
		itemMinus.setText(i18Message.MENOS_ESPECTRO);
		itemMinus.setImage(imagenMinus);

		final ToolItem separator3 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator3.setWidth(50);

		// Creamos la zona de recarga

		ToolItem itemReload = new ToolItem(toolBar, SWT.PUSH);
		Image imagenReload = new Image(Display.getCurrent(), i18Message.RUTA_RELOAD);
		itemReload.setToolTipText(i18Message.RELOAD);
		itemReload.setText(i18Message.RELOAD);
		itemReload.setImage(imagenReload);

		final ToolItem separator5 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator5.setWidth(60);

		// Campo de texto para introducir donde queremos guardar la foto
		ToolItem sep2 = new ToolItem(toolBar, SWT.SEPARATOR);
		textoFotoGuardar = new Text(toolBar, SWT.NONE);
		sep2.setWidth(120);
		sep2.setControl(textoFotoGuardar);

		ToolItem itemSave = new ToolItem(toolBar, SWT.PUSH);
		Image imagenSave = new Image(Display.getCurrent(), i18Message.RUTA_SAVE);
		itemSave.setToolTipText(i18Message.SAVE);
		itemSave.setText(i18Message.SAVE);
		itemSave.setImage(imagenSave);

		final ToolItem separator4 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator4.setWidth(400);

		// Creamos boton atras
		ToolItem itemBack = new ToolItem(toolBar, SWT.PUSH);
		Image imagenBack = new Image(Display.getCurrent(), i18Message.RUTA_BACK);
		itemBack.setToolTipText(i18Message.BACK);
		itemBack.setText(i18Message.BACK);
		itemBack.setImage(imagenBack);

		funciones.funcionAtras(shell, itemBack, passwd);

		toolBar.pack();

		// Creamos la seccion donde se mostraran las fotos
		Composite compositeFotos = new Composite(parent, SWT.NONE);
		GridLayout glFotos = new GridLayout(2, false);
		glFotos.marginTop = 50;
		compositeFotos.setBackground(ColorHelper.COLOR_BLACK);
		compositeFotos.setLayout(glFotos);

		// Label para las fotos
		labelFoto = new Label(compositeFotos, SWT.NONE);
		labelFoto2 = new Label(compositeFotos, SWT.NONE);

		Image im2 = new Image(Display.getCurrent(), i18Message.RUTA_BLANCO);
		im2 = resizeHelper.resize(im2, 580, 420);
		labelFoto.setImage(im2);

		Image im3 = new Image(Display.getCurrent(), i18Message.RUTA_BLANCO);
		im3 = resizeHelper.resize(im3, 580, 420);
		labelFoto2.setImage(im2);

		// Zona donde implementamos los listeners
		funcionAbrir(itemPush, parent);
		funcionAumentar(itemPlus);
		funcionDisminuir(itemMinus);
		funcionRecargar(itemReload);
		funcionGuardar(itemSave);

	}

	@Override
	public void run() {
		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(1200, 600);
		shell.setBackground(ColorHelper.COLOR_BLACK);

		shellHelper.noCerrarShell(shell);

		createToolBar(shell);

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
