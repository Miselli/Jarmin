package Jarmin;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Main extends BasicGame{

	//declaring images
	Image back;
	Image icon;

	float x = 0.0f;
	float y = 0.0f;
	float speed = 1.0f;

	public Main(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) throws SlickException{
		
		AppGameContainer app = new AppGameContainer (new Main("Slick2d"));
		app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), false);
		System.out.println(app.getScreenWidth() + " " + app.getScreenHeight());
		app.start();
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);

		//		g.drawImage(icon, x, y);

		g.drawImage(icon, x+350,y+150);
		g.drawString("ALBUM 1", x+375, y+255);

		g.drawImage(icon, x+650,y+150);
		g.drawString("ALBUM 2", x+675, y+255);

		g.drawImage(icon, x+950,y+150);
		g.drawString("ALBUM 3", x+975, y+255);

		g.drawImage(icon, x+350,y+450);
		g.drawString("ALBUM 4", x+375, y+555);

		g.drawImage(icon, x+650,y+450);
		g.drawString("ALBUM 5", x+675, y+555);

		g.drawImage(icon, x+950,y+450);
		g.drawString("ALBUM 6", x+975, y+555);

		g.drawString("Scorri la mano verso destra per cambiare pagina", 500, 700);
		g.drawLine(0, 690, gc.getScreenWidth(), 690);

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

		back = new Image("background.jpg");
		icon = new Image("Music-icon.png");
		icon.setName("album1");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input in = gc.getInput();//asks slick2d qhat key are being pressed
		
		if(in.isKeyDown(Input.KEY_RIGHT)){
			x += speed*delta;
		}
		if(in.isKeyDown(Input.KEY_LEFT)){
			x -= speed*delta;
		}

//		if (in.isKeyDown(Input.KEY_P)){
//			gc.setPaused(true);
//		}
//		if (in.isKeyDown(Input.KEY_R)){
//			gc.setPaused(false);
//		}
	}

}