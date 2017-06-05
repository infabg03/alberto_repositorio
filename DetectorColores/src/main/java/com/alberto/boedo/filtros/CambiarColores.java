package com.alberto.boedo.filtros;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HLS;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2Lab;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2Luv;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.eclipse.swt.internal.win32.LOGBRUSH;

import com.alberto.boedo.naming.i18Message;
import com.alberto.boedo.vista.VentanaCambioColores;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class CambiarColores {

	private final static Logger log = Logger.getLogger(CambiarColores.class);

	public static String conversor(String ruta, int opcion) {

		IplImage imagen;
		String foto = "";

		try {
			imagen = IplImage.createFrom(ImageIO.read(new File(ruta)));

			switch (opcion) {
			case 1:
				cvSaveImage(i18Message.RUTA_ORIGINAL, imagen);
				foto = i18Message.RUTA_ORIGINAL;
				break;

			case 2:
				IplImage cieimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, cieimg, CV_BGR2Lab);
				cvSaveImage(i18Message.RUTA_CIE, cieimg);
				foto = i18Message.RUTA_CIE;
				break;

			case 3:
				IplImage grayimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 1);
				cvCvtColor(imagen, grayimg, CV_BGR2GRAY);
				cvSaveImage(i18Message.RUTA_GRAY, grayimg);
				foto = i18Message.RUTA_GRAY;
				break;

			case 4:
				IplImage hlsimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, hlsimg, CV_BGR2HLS);
				cvSaveImage(i18Message.RUTA_HLS, hlsimg);
				foto = i18Message.RUTA_HLS;
				break;

			case 5:
				IplImage luvimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, luvimg, CV_BGR2Luv);
				cvSaveImage(i18Message.RUTA_LUV, luvimg);
				foto = i18Message.RUTA_LUV;
				break;

			case 6:
				IplImage hsvimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, hsvimg, CV_BGR2HSV);
				cvSaveImage(i18Message.RUTA_HSV, hsvimg);
				foto = i18Message.RUTA_HSV;
				break;

			default:
				cvSaveImage(i18Message.RUTA_ORIGINAL, imagen);
				foto = i18Message.RUTA_ORIGINAL;
				break;
			}

			cvWaitKey();

		} catch (IOException e) {
			log.warn(e.getMessage());
		}
		return foto;

	}

}
