package Jarmin;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.highgui.VideoCapture;

public class BallTracking { 
	String[] Songs = new String[50];
	String[] Albums = new String[50];
	String[] Artists = new String[50];
	boolean found=false;

	public BallTracking() throws InterruptedException{

		fileExplorer fw = new fileExplorer(); 
		fw.fileWalker("C:\\Users\\Miso\\Desktop\\LibreriaMusicale", 0 );
		Songs=fw.getSongs();
		Albums=fw.getAlbums();
		Artists=fw.getArtists();
	}

	public synchronized void listen(Thread thread, String foundWord ) {
		
		boolean fileFound=false;

		if(foundWord!=null) {
			for(int k=0; k<Artists.length && (!fileFound); k++) {
				if(foundWord.equalsIgnoreCase(Artists[k])) {
					System.out.println("Artista: " + foundWord);
					fileFound=true;
				}
			}
			for(int l=0; l<Albums.length && (!fileFound); l++) {
				if(foundWord.equalsIgnoreCase(Albums[l])) {
					System.out.println("Album: " + foundWord);
					fileFound=true;
				}
			}
			try {
				for(int m=0; m<Songs.length && (!fileFound); m++) {

					if(Songs[m].contains(foundWord)) { // se non trova parole che coincidono va in null pointer exception
						foundWord=Songs[m];
						System.out.println("Canzone: " + foundWord);
						fileFound=true;											
					}

				}
			}
			catch(java.lang.NullPointerException e) {
				System.out.println("File non trovato");
			}

		}

	}
	public void Jarmin () throws IOException, InterruptedException{  

		// Carico la libreria nativa  
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  

		//Creo i JFrame e i JPanel
		JFrame frame1 = new JFrame("Camera");  
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame1.setSize(xSize,ySize);
		frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());  

		JFrame frame2 = new JFrame("InterfacciaUtente");  
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(frame1.getWidth(), frame1.getHeight());

		myPanel panel1 = new myPanel();  
		frame1.setContentPane(panel1);  
		frame1.setVisible(true);  

		myPanel panel2 = new myPanel();  
		panel2.setBackground(Color.black);

		//Carico le immagini dei semafori sul JPanel
		BufferedImage semaphoreGreen = ImageIO.read(new File("C:\\Users\\Miso\\workspace\\Jarmin\\src\\Verde.png"));
		JLabel picLabel = new JLabel(new ImageIcon(semaphoreGreen));
		picLabel.setLocation(new java.awt.Point(50,50));
		panel2.add(picLabel);

		BufferedImage semaphoreRed = ImageIO.read(new File("C:\\Users\\Miso\\workspace\\Jarmin\\src\\Rosso.png"));
		JLabel picLabel2 = new JLabel(new ImageIcon(semaphoreRed));
		picLabel2.setLocation(new java.awt.Point(50,50));
		panel2.add(picLabel2);

		frame2.setContentPane(panel2);  
		frame2.setVisible(true);

		//Read the video stream  
		VideoCapture capture =new VideoCapture(0);

		Mat webcam_image=new Mat(); 

		Point centerToVect = new Point();
		Point[] vectGesture = new Point[20];
		String gest=null;
		int sleep=0;
		int index=0;

		if( capture.isOpened())  
		{  
			//Infinite cycle
			while( true )  {  

				//Take the image from webcam and put in a matrix 
				capture.read(webcam_image);  

				if( !webcam_image.empty() )  
				{  

					findCircles redCircle = new findCircles(webcam_image);
					webcam_image = redCircle.getCircles();
					centerToVect = redCircle.getCenter();

					if(sleep==0) { 
						picLabel.setVisible(true);
						picLabel2.setVisible(false);

						vectGesture[index]=centerToVect;
						index++;

						if ( index == 20) {

							findGesture gesture = new findGesture();
							gest = gesture.getGesture(vectGesture);
							
							if(gest!=null){

								sleep=50;

								if(gest=="right")
									System.out.println("Right");
								if(gest=="left")
									System.out.println("Left");
								if(gest=="down")
									System.out.println("Down");
								if(gest=="up") {
									findSound sound = new findSound(this);
									sound.start();											
								}
							}
							index=0;
						}
					}
					else {
						picLabel.setVisible(false);
						picLabel2.setVisible(true);
						sleep--;
					}
					// 5. Display the image  
					panel1.setimagewithMat(webcam_image); 
					frame1.repaint(); 
				}
				else  
				{  
					System.out.println(" --(!) No captured frame -- Break!");  
					break;  
				}  
			}
		}
		return;
	}  


	public static void main (String arg[]) throws IOException, InterruptedException{
		BallTracking b = new BallTracking();
		b.Jarmin();
	}
}

