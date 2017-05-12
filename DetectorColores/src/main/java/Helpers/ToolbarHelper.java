package Helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ToolbarHelper {

	private static String selected;

	public static ToolBar InicializaSelectorToolbar(Composite parent, String rutaImagen) {

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

		return toolBar;

	}

}
