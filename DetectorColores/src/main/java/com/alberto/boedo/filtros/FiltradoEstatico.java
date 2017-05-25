package com.alberto.boedo.filtros;

import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.alberto.boedo.naming.i18Message;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;

/**
 *
 * @author Alberto
 */

@Component("filtradoEstatico")
public class FiltradoEstatico implements IfiltradoEstatico {

	private static double value = 150;

	public void aumentarValor() {
		if (!(value > 245))
			value += 10;
	}

	public void disminuirValor() {
		if (!(value < 10))
			value -= 10;
	}

	public void setValorOriginal() {
		value = 150;
	}

	@Override
	public List<String> conversor(String rutaImagenSeleccionada, String color, String passwd) {

		IplImage img1, imghsv, imgbin;
		List<String> parImagenes = new ArrayList<String>(2);

		try {
			BufferedImage imagen = ImageIO.read(new File(rutaImagenSeleccionada));
			img1 = IplImage.createFrom(imagen);

			imghsv = cvCreateImage(cvGetSize(img1), 8, 3);
			imgbin = cvCreateImage(cvGetSize(img1), 8, 1);

			cvCvtColor(img1, imghsv, CV_BGR2HSV);
			// Rango de colores por los que se va a filtrar minc maxc
			CvScalar minc, maxc;

			switch (color) {
			default:
				minc = cvScalar(95, value, 75, 0);
				maxc = cvScalar(135, 255, 255, 0);
				break;
			case i18Message.COLOR_AZUL:
				minc = cvScalar(95, value, 75, 0);
				maxc = cvScalar(135, 255, 255, 0);
				break;
			case i18Message.COLOR_ROJO:
				minc = cvScalar(0, value, 75, 0);
				maxc = cvScalar(10, 255, 255, 0);
				break;
			case i18Message.COLOR_VERDE:
				minc = cvScalar(25, value, 75, 0);
				maxc = cvScalar(70, 255, 255, 0);
				break;
			case i18Message.COLOR_VIOLETA:
				minc = cvScalar(135, value, 75, 0);
				maxc = cvScalar(150, 255, 255, 0);
				break;
			case i18Message.COLOR_AMARILLO_NARANJA:
				minc = cvScalar(10, value, 75, 0);
				maxc = cvScalar(25, 255, 255, 0);
				break;
			}

			cvInRangeS(imghsv, minc, maxc, imgbin);

			cvWaitKey();

			cvSaveImage(i18Message.RUTA_ORIGINAL_EST, img1);
			parImagenes.add(i18Message.RUTA_ORIGINAL_EST);
			cvSaveImage(i18Message.RUTA_FILTRADA_EST, imgbin);
			parImagenes.add(i18Message.RUTA_FILTRADA_EST);

			cvReleaseImage(imghsv);
			cvReleaseImage(imgbin);

			return parImagenes;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No ha introducido ninguna foto");
		}

		return null;

	}

}
