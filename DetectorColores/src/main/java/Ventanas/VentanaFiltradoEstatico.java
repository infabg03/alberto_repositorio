package Ventanas;

import java.util.List;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Filtros.FiltradoEstatico;
import Helpers.ColorHelper;
import Helpers.GoBackHelper;
import Helpers.ImageResizeHelper;
import Helpers.MessageDialogHelper;
import Helpers.WindowCenterHelper;

public class VentanaFiltradoEstatico implements Runnable {

	private final String rutaImagen = "src/resources/abrir.png";
	private final String rutaImagenPlus = "src/resources/add.png";
	private final String rutaImagenMinus = "src/resources/minus.png";
	private final String rutaImagenReload = "src/resources/refresh.png";
	private final String rutaImagenBack = "src/resources/back.png";
	List<String> listaFotos;

	Label labelFoto;
	Label labelFoto2;
	Combo combo;
	String selected;
	Shell shell;
	String passwd;

	public VentanaFiltradoEstatico(String passwd) {
		super();
		this.passwd = passwd;
	}

	public void funcionSetearFotos() {
		if (selected != null) {
			listaFotos = FiltradoEstatico.conversor(selected, combo.getText());

			Image original = new Image(Display.getCurrent(), listaFotos.get(0));
			original = ImageResizeHelper.resize(original, 580, 420);
			Image filtrada = new Image(Display.getCurrent(), listaFotos.get(1));
			filtrada = ImageResizeHelper.resize(filtrada, 580, 420);

			labelFoto.setImage(original);
			labelFoto2.setImage(filtrada);
		} else {
			MessageDialogHelper.aceptarDialog(shell, "Informacion de fotografia",
					"Debe seleccionar previamente una fotografia");
		}
	}

	public void funcionCambioCombo() {
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FiltradoEstatico.setValorOriginal();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

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

	public void funcionAumentar(ToolItem itemPlus) {
		itemPlus.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FiltradoEstatico.aumentarValor();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void funcionDisminuir(ToolItem itemMinus) {
		itemMinus.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FiltradoEstatico.disminuirValor();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void createToolBar(final Shell parent) {

		// Creamos la toolbar
		ToolBar toolBar = new ToolBar(parent, SWT.WRAP);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		toolBar.setBackground(ColorHelper.COLOR_AQUAMARINE);

		// Anhadimos al toolbar el iconito para ejecutar un filechooser
		ToolItem itemPush = new ToolItem(toolBar, SWT.PUSH);
		Image imagen = new Image(Display.getCurrent(), rutaImagen);
		itemPush.setToolTipText("Abrir");
		itemPush.setText("Abrir");
		itemPush.setImage(imagen);

		final ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);
		separator.setWidth(20);

		// Anhadimos combo selector colores
		ToolItem sep = new ToolItem(toolBar, SWT.SEPARATOR);
		combo = new Combo(toolBar, SWT.READ_ONLY);
		sep.setText("Color");
		combo.add("Azul");
		combo.add("Verde");
		combo.add("Rojo");
		combo.add("Violeta");
		combo.add("Amarillo/Naranja");
		combo.setText("Azul");
		combo.pack();
		sep.setWidth(combo.getSize().x);
		sep.setControl(combo);

		final ToolItem separator2 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator2.setWidth(50);

		// Creamos la zona que aumenta el parametro

		ToolItem itemPlus = new ToolItem(toolBar, SWT.PUSH);
		Image imagenPlus = new Image(Display.getCurrent(), rutaImagenPlus);
		itemPlus.setToolTipText("Aumentar");
		itemPlus.setText("Mas espectro");
		itemPlus.setImage(imagenPlus);

		ToolItem itemMinus = new ToolItem(toolBar, SWT.PUSH);
		Image imagenMinus = new Image(Display.getCurrent(), rutaImagenMinus);
		itemMinus.setToolTipText("Disminuir");
		itemMinus.setText("Menos espectro");
		itemMinus.setImage(imagenMinus);

		final ToolItem separator3 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator3.setWidth(50);

		// Creamos la zona de recarga

		ToolItem itemReload = new ToolItem(toolBar, SWT.PUSH);
		Image imagenReload = new Image(Display.getCurrent(), rutaImagenReload);
		itemReload.setToolTipText("Recargar");
		itemReload.setText("Recargar");
		itemReload.setImage(imagenReload);

		final ToolItem separator4 = new ToolItem(toolBar, SWT.SEPARATOR);
		separator4.setWidth(620);

		ToolItem itemBack = new ToolItem(toolBar, SWT.PUSH);
		Image imagenBack = new Image(Display.getCurrent(), rutaImagenBack);
		itemBack.setToolTipText("Atras");
		itemBack.setText("Atras");
		itemBack.setImage(imagenBack);

		GoBackHelper.funcionAtras(shell, itemBack, passwd);

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

		Image im2 = new Image(Display.getCurrent(), "src/resources/blanco.jpg");
		im2 = ImageResizeHelper.resize(im2, 580, 420);
		labelFoto.setImage(im2);

		Image im3 = new Image(Display.getCurrent(), "src/resources/blanco.jpg");
		im3 = ImageResizeHelper.resize(im3, 580, 420);
		labelFoto2.setImage(im2);

		// Zona donde implementamos los listeners
		funcionAbrir(itemPush, parent);
		funcionAumentar(itemPlus);
		funcionDisminuir(itemMinus);
		funcionRecargar(itemReload);

	}

	@Override
	public void run() {
		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(1200, 600);
		shell.setBackground(ColorHelper.COLOR_BLACK);

		// Deshabilita boton cerrado de la shell
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
			}
		});

		createToolBar(shell);

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
