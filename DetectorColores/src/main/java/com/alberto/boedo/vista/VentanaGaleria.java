package com.alberto.boedo.vista;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import com.alberto.boedo.factoria.BeansFactory;
import com.alberto.boedo.helpers.ColorHelper;
import com.alberto.boedo.helpers.ImageResizeHelper;
import com.alberto.boedo.helpers.WindowCenterHelper;
import com.alberto.boedo.modelo.Foto;
import com.alberto.boedo.modelo.Persona;
import com.alberto.boedo.modelo.PersonaDAO;

public class VentanaGaleria implements Runnable {
	static List<Foto> listaFotos;
	private String passwd;
	PersonaDAO personaDAO = BeansFactory.getBean(PersonaDAO.class);
	int i = 1;

	public VentanaGaleria(String passwd) {
		super();
		this.passwd = passwd;
	}

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}

	@Override
	public void run() {
		final Shell shell;
		final Display display = new Display();
		shell = new Shell(display, SWT.MIN);
		shell.setLayout(new GridLayout(1, true));
		shell.setSize(800, 600);

		Persona p = personaDAO.getPersona(passwd);
		listaFotos = p.getFotos();

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		composite.setLayoutData(gridData);
		composite.setBackground(ColorHelper.COLOR_BLACK);

		// Creamos la toolbar
		ToolBar toolBar = new ToolBar(composite, SWT.WRAP);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolBar.setBackground(ColorHelper.COLOR_BLACK);

		// Label donde se insertaran las fotos
		final Label label = new Label(composite, SWT.BORDER);
		Image imagen = new Image(display, listaFotos.get(i).getRuta());
		imagen = ImageResizeHelper.resize(imagen, 770, 500);
		label.setImage(imagen);

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
