package alberto.boedo.wirtz.DetectorColores;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_ANY;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateCameraCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvQueryFrame;
import static com.googlecode.javacv.cpp.opencv_highgui.cvReleaseCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alberto
 */
public class FiltradoDinamicoTest {
    
        public static void main(String[] args) throws IOException {

            opencv_core.IplImage img1, imghsv, imgbin;



            imghsv= cvCreateImage(cvSize(640,480),8,3);
            imgbin= cvCreateImage(cvSize(640,480),8,1);
            CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);

            int i=1;

            while(i==1){

             img1= cvQueryFrame(capture1);

             if(img1==null)
                 break;

            cvCvtColor(img1,imghsv,CV_BGR2HSV);
            // Rango de colores por los que se va a filtrar minc maxc
            opencv_core.CvScalar minc,maxc;
            minc = cvScalar(95,50,75,0);
            maxc=cvScalar(135,255,255,0);
            cvInRangeS(imghsv,minc,maxc,imgbin);

            cvShowImage("color",img1);
            cvShowImage("filtrada",imgbin);
            char c=(char)cvWaitKey(15);
            if(c=='q')
                break;

            }
            cvReleaseCapture(capture1);
            cvReleaseImage(imgbin);
            cvReleaseImage(imghsv);

        }
    
}