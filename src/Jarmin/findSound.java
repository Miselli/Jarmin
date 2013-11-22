package Jarmin;

public class findSound extends Thread {
	public BallTracking manager;
	String foundWord=null;

	public findSound(BallTracking man) { 
		this.manager = man;
	}

	public void run() {
		recognitionTest findVoice = new recognitionTest();
		this.foundWord=findVoice.recognize();	
		manager.listen(this, foundWord);
	}
}
