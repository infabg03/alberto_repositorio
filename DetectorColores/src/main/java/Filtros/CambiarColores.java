package Filtros;

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

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class CambiarColores {

	public static String conversor(String ruta, int opcion) {

		// Si no cargamos asi las imagenes casca
		IplImage imagen;
		String foto = "";

		try {
			imagen = IplImage.createFrom(ImageIO.read(new File(ruta)));

			switch (opcion) {
			case 1:
				cvSaveImage("src/resources/tempFotos/Original.jpg", imagen);
				foto = "src/resources/tempFotos/Original.jpg";
				break;

			case 2:
				IplImage cieimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, cieimg, CV_BGR2Lab);
				cvSaveImage("src/resources/tempFotos/CIE.jpg", cieimg);
				foto = "src/resources/tempFotos/CIE.jpg";
				break;

			case 3:
				IplImage grayimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 1);
				cvCvtColor(imagen, grayimg, CV_BGR2GRAY);
				cvSaveImage("src/resources/tempFotos/GRAY.jpg", grayimg);
				foto = "src/resources/tempFotos/GRAY.jpg";
				break;

			case 4:
				IplImage hlsimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, hlsimg, CV_BGR2HLS);
				cvSaveImage("src/resources/tempFotos/HLS.jpg", hlsimg);
				foto = "src/resources/tempFotos/HLS.jpg";
				break;

			case 5:
				IplImage luvimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, luvimg, CV_BGR2Luv);
				cvSaveImage("src/resources/tempFotos/LUV.jpg", luvimg);
				foto = "src/resources/tempFotos/LUV.jpg";
				break;

			case 6:
				IplImage hsvimg = cvCreateImage(cvGetSize(imagen), IPL_DEPTH_8U, 3);
				cvCvtColor(imagen, hsvimg, CV_BGR2HSV);
				cvSaveImage("src/resources/tempFotos/HSV.jpg", hsvimg);
				foto = "src/resources/tempFotos/HSV.jpg";
				break;

			default:
				cvSaveImage("src/resources/tempFotos/Original.jpg", imagen);
				foto = "src/resources/tempFotos/Original.jpg";
				break;
			}

			cvWaitKey();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foto;

	}

}
