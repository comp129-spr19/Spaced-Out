package testing;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import utility.CollisionHandler;

public class CollisionTester extends GraphicsProgram implements ActionListener{
	
public static final int SPACEBETWEEN = 40; 
public static final int VELOCITY = 2;
GRect character;
//program.addKeyListeners();
	public void run() {
		GRect square = new GRect(40,40,10,10);
		GOval cir = new GOval(80,80,10,10);
		
		character = new GRect(100,100,10,10);
		character.setFilled(true);
		add(square);
		add(cir);
		add(character);
		addKeyListeners();
		
		while(true) {
			
			System.out.println("RUNNING");
			if (CollisionHandler.checkRectCircCollison(cir,character )) {
				remove(cir);
			}
			
			if (CollisionHandler.checkRectRectCollision(character, square)) {
				remove(square);
			}
		}
	}
	@Override 
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			character.move(-1 * VELOCITY, 0);
		}
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			character.move(VELOCITY, 0);
		}
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			character.move(0, -1 *VELOCITY);
		}
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			character.move(0, VELOCITY);
		}
		
	}

}
