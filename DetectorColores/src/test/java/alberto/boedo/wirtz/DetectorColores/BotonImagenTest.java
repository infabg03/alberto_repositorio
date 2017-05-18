package alberto.boedo.wirtz.DetectorColores;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.alberto.boedo.componentes.BotonImagen;

public class BotonImagenTest {
	
	private Button btnImagen;

	public void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());

		btnImagen = new BotonImagen().getBotonImagen(display,shell,"src/resources/return.png");
		
		
		shell.open();
		
		// con esto hacemos que la shell no se desvanezca en tiempo de ejecucion
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				display.dispose();
	}

}
