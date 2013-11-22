package Jarmin;

import org.opencv.core.Point;


public class findGesture {

	float distX;
	float distY;

	float minDist = 100;

	Point[] vectGesture;
	String gest=null;

	public String getGesture( Point[] vect) { 

		vectGesture=vect;
		int points=0;

		distX = (float) (vectGesture[19].x - vectGesture[0].x);
		distY = (float) (vectGesture[19].y - vectGesture[0].y);
		for (int i=0; i<20; i++) {
			if (vectGesture[i].x!= 0 && vectGesture[i].y!=0)
				points++;
		}
		if( distX > minDist && ( distX >  Math.abs(distY)) && points>14){
			gest = "right";	
			return gest;
		}

		if( Math.abs(distX) > minDist && Math.abs(distX) > Math.abs(distY) && points>14){
			gest = "left";
			return gest;
		}

		if (distY > minDist &&  distY > Math.abs(distX) && points>14){
			gest = "down";
			return gest;
		}

		if (Math.abs(distY) > minDist &&  Math.abs(distY) > Math.abs(distX) && points>14){
			gest = "up";
			return gest;
		}
		return gest;
	}
}
