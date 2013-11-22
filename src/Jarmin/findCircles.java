package Jarmin;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class findCircles {
	Mat matrix = new Mat();
	Mat hsv_image=new Mat();  
	Mat thresholded=new Mat();  
	Mat thresholded2=new Mat();
	Point centerToReturn = new Point();

	public findCircles(Mat image){

		image.copyTo(matrix);

	}
	public Mat getCircles(){
		Mat array255=new Mat(matrix.height(),matrix.width(),CvType.CV_8UC1);  
		array255.setTo(new Scalar(255));  
		Mat distance=new Mat(matrix.height(),matrix.width(),CvType.CV_8UC1);  
		List<Mat> lhsv = new ArrayList<Mat>(3);      

		Mat circles = new Mat(); // No need to initialize it.  

		//Declaration of the Color's range scalar 
		Scalar hsv_min = new Scalar(0, 50, 50, 0);  
		Scalar hsv_max = new Scalar(6, 255, 255, 0);  
		Scalar hsv_min2 = new Scalar(175, 50, 50, 0);  
		Scalar hsv_max2 = new Scalar(179, 255, 255, 0);  

		Imgproc.cvtColor(matrix, hsv_image, Imgproc.COLOR_BGR2HSV);  
		Core.inRange(hsv_image, hsv_min, hsv_max, thresholded);           
		Core.inRange(hsv_image, hsv_min2, hsv_max2, thresholded2);  
		Core.bitwise_or(thresholded, thresholded2, thresholded);  

		Core.split(hsv_image, lhsv); // We get 3 2D one channel Mats  
		Mat S = lhsv.get(1);  
		Mat V = lhsv.get(2);  
		Core.subtract(array255, S, S);  
		Core.subtract(array255, V, V);  
		S.convertTo(S, CvType.CV_32F);  
		V.convertTo(V, CvType.CV_32F);  
		Core.magnitude(S, V, distance); 
		Core.inRange(distance,new Scalar(0.0), new Scalar(200.0), thresholded2);  
		Core.bitwise_and(thresholded, thresholded2, thresholded);  

		// Apply the Hough Transform to find the circles  
		Imgproc.GaussianBlur(thresholded, thresholded, new Size(9,9),0,0);  
		Imgproc.HoughCircles(thresholded, circles, Imgproc.CV_HOUGH_GRADIENT, 2, thresholded.height()/4, 500, 50, 0, 0);    
		int rows = circles.rows();  
		int elemSize = (int)circles.elemSize(); // Returns 12 (3 * 4bytes in a float)  

		float[] data2 = new float[rows * elemSize/4];  

		if (data2.length>0){  

			circles.get(0, 0, data2); // Points to the first element and reads the whole thing into data2  

			for(int i=0; i<data2.length; i=i+3) {  
				Point center= new Point(data2[i], data2[i+1]); 
				//				System.out.println("Centro: ("+ center.x + "," + center.y + ")");
				Core.ellipse( matrix, center, new Size(90,90), 0, 0, 
						360, new Scalar( 255, 0, 255 ), 4, 8, 0 ); 
				centerToReturn=center;
			}
		}
		else {
			//			System.out.println("Centro: (0,0)");
		}
		return matrix;

	}

	public Point getCenter() {
		return centerToReturn;
	}
}
