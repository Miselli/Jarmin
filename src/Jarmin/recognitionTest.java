package Jarmin;

public class recognitionTest  { 
	String s=null;

	public recognitionTest(){

	}	

	public String recognize() {

		boolean quit=false;
		long StartTime = System.nanoTime();

		voce.SpeechInterface.init("lib",true, "gram", "digits");

		System.out.println("Speak into the microphone.");

		while (!quit)
		{
			// Normally, applications would do application-specific things 
			// here.  For this sample, we'll just sleep for a little bit.
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
			}

			while (voce.SpeechInterface.getRecognizerQueueSize() > 0 ){
				s = voce.SpeechInterface.popRecognizedString();

				if(System.nanoTime()-StartTime>5000) {
					quit=true;
				}
			}
		}//chiudo while(!quit)		

		voce.SpeechInterface.destroy();

		return s;

	}//chiudo metodo
} 