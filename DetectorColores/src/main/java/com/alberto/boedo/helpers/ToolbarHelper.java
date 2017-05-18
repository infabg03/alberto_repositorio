package com.alberto.boedo.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.alberto.boedo.naming.i18Message;

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
		itemPush.setToolTipText(i18Message.OPEN);
		itemPush.setText(i18Message.OPEN);
		itemPush.setImage(imagen);

		return toolBar;

	}

}
