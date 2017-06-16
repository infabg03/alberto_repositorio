package com.alberto.boedo.componentes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LabeledCombo {

	private Composite parent;
	private String textoLabel;
	private int estilo;
	private Combo combo;

	/**
	 * Crea un combo que ya contiene una label asociada
	 * 
	 * @param parent
	 *            El componente padre del que colgara el boton.
	 * @param textoLabel
	 *            El texto de la etiqueta.
	 * @param estilo
	 *            El estilo del combo.
	 */
	public LabeledCombo(Composite parent, String textoLabel, int estilo) {
		super();
		this.parent = parent;
		this.textoLabel = textoLabel;
		this.estilo = estilo;
		generarLabeledCombo(parent, textoLabel, estilo);
	}

	/**
	 * Llamada que se utiliza desde el constructor para general el combo
	 * etiquetado.
	 * 
	 * @param parent
	 *            El componente padre del que colgara el boton.
	 * @param textoLabel
	 *            El texto de la etiqueta.
	 * @param estilo
	 *            El estilo del combo.
	 */
	public void generarLabeledCombo(Composite parent, String textoLabel, int estilo) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		composite.setLayout(gridLayout);

		Label label = new Label(composite, SWT.NONE);
		label.setText(textoLabel);

		combo = new Combo(composite, estilo);
		combo.setTouchEnabled(false);

	}

	/**
	 * Añade un item al combo.
	 * 
	 * @param item
	 *            Item que se añadira al combo.
	 * 
	 */
	public void add(String item) {
		combo.add(item);
	}
	
	public String getSelected(){
		return combo.getText();
	}

}
