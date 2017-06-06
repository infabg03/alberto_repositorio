package com.alberto.boedo.componentes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LabeledEditText {

	private Composite parent;
	private String textoInterior;
	private String textoLabel;
	private boolean passwd;
	private int filtros;
	private int ancho;
	private Text text;

	private boolean escribo = false;

	/**
	 * Setea un campo de texto.
	 * 
	 * @param text
	 *            Un campo de texto.
	 */
	public void setText(Text text) {
		this.text = text;
	}

	/**
	 * Crea un campo de texto que ya tiene una etiqueta asociada.
	 * 
	 * @param parent
	 *            El componente padre del que colgara el campo de texto.
	 * @param estilo
	 *            El estilo del campo de texto.
	 * @param textoInterior
	 *            Texto que contendra el campo de texto.
	 * @param textoLabel
	 *            Texto de la etiqueta.
	 * @param passwd
	 *            Texto del campo password.
	 * @param ancho
	 *            Ancho de la etiqueta.
	 */
	public LabeledEditText(Composite parent, int estilo, String textoInterior, String textoLabel, boolean passwd,
			int ancho) {
		this.parent = parent;
		this.textoInterior = textoInterior;
		this.textoLabel = textoLabel;
		this.passwd = passwd;
		this.ancho = ancho;
		getLabelEditText(parent, textoInterior, textoLabel, passwd, ancho);

	}

	/**
	 * Funcion que se llama desde el constructor para generar el campo de texto
	 * etiquetado.
	 * 
	 * @param parent
	 *            El componente padre del que colgara el campo de texto.
	 * @param textoInterior
	 *            Texto que contendra el campo de texto.
	 * @param textoLabel
	 *            Texto de la etiqueta.
	 * @param passwd
	 *            Texto del campo password.
	 * @param ancho
	 *            Ancho de la etiqueta.
	 */
	public void getLabelEditText(Composite parent, String textoInterior, String textoLabel, boolean passwd, int ancho) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		composite.setLayout(gridLayout);

		Label label = new Label(composite, SWT.LEFT);
		label.setText(textoLabel);
		GridData gd = new GridData();
		gd.widthHint = ancho;
		label.setLayoutData(gd);

		text = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text.setToolTipText(textoInterior);
		text.setText(textoInterior);
		GridData gridData2 = new GridData(SWT.BEGINNING, SWT.FILL, true, true);
		gridData2.widthHint = 180;
		gridData2.heightHint = SWT.DEFAULT;
		text.setLayoutData(gridData2);
		if (passwd)
			text.setEchoChar('*');

		// Hacemos esto para el borrado de los textos de los combos
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (!escribo) {
					text.setText("");
					escribo = true;
				}
			}
		});

	}

	/**
	 * Obtiene el texto de un texbox.
	 * 
	 * @return El texto de un texbox.
	 */
	public String getTexto() {
		return text.getText();
	}

	/**
	 * Devuelve un textbox.
	 * 
	 * @return Un textbox.
	 */
	public Text getText() {
		return text;
	}

	/**
	 * Setea el texto al texbox.
	 * 
	 * @param texto
	 *            Texto que se quiere insertar en el campo de texto.
	 */
	public void setText(String texto) {
		text.setText(texto);
	}

	/**
	 * Hace que un campo de texto deje de ser editable.
	 * 
	 */
	public void setDisable() {
		text.setEditable(false);
	}
}
