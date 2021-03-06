package com.alberto.boedo.filtros;

import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_ANY;
import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateCameraCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvQueryFrame;
import static com.googlecode.javacv.cpp.opencv_highgui.cvReleaseCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import org.springframework.stereotype.Component;

import com.alberto.boedo.naming.i18Message;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

@Component
public class FiltradoDinamico implements IFiltradoDinamico {

	/**
	 * Muestra dos ventanas, una en la que se ve la imagen real captada por la
	 * webcam y otra donde solo se ven los objetos del color por el que estamos
	 * filtrando.
	 * 
	 */
	@Override
	public void execute(String color) {
		opencv_core.IplImage img1, imghsv, imgbin;

		imghsv = cvCreateImage(cvSize(640, 480), 8, 3);
		imgbin = cvCreateImage(cvSize(640, 480), 8, 1);
		CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);

		int i = 1;

		while (i == 1) {

			img1 = cvQueryFrame(capture1);

			if (img1 == null)
				break;

			cvCvtColor(img1, imghsv, CV_BGR2HSV);
			// Rango de colores por los que se va a filtrar minc maxc
			opencv_core.CvScalar minc, maxc;
			System.out.println("El color es: "+color);
			switch (color) {
			default:
				minc = cvScalar(95, 150, 75, 0);
				maxc = cvScalar(135, 255, 255, 0);
				break;
			case i18Message.COLOR_AZUL:
				minc = cvScalar(95, 150, 75, 0);
				maxc = cvScalar(135, 255, 255, 0);
				break;
			case i18Message.COLOR_ROJO:
				minc = cvScalar(0, 150, 75, 0);
				maxc = cvScalar(10, 255, 255, 0);
				break;
			case i18Message.COLOR_VERDE:
				minc = cvScalar(25, 150, 75, 0);
				maxc = cvScalar(70, 255, 255, 0);
				break;
			case i18Message.COLOR_VIOLETA:
				minc = cvScalar(135, 150, 75, 0);
				maxc = cvScalar(150, 255, 255, 0);
				break;
			case i18Message.COLOR_AMARILLO_NARANJA:
				minc = cvScalar(10, 150, 75, 0);
				maxc = cvScalar(25, 255, 255, 0);
				break;
			}
			cvInRangeS(imghsv, minc, maxc, imgbin);

			cvShowImage(i18Message.LABEL_COLOR, img1);
			cvShowImage(i18Message.LABEL_FILTRADA, imgbin);
			char c = (char) cvWaitKey(15);
			if (c == 'q') {
				break;

			}

		}
		cvReleaseCapture(capture1);
		cvReleaseImage(imgbin);
		cvReleaseImage(imghsv);

	}

}
