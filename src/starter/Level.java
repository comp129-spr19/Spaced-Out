package starter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.graphics.*;
import game.CollisionChecker;
import game.Payload;
import game.Player;
import game.Portal;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class Level extends GraphicsPane implements ActionListener {

	/* CONSTANTS */
	private MainApplication program; //use this 'program.something' for all program calls
	
	public static final int VELOCITY = 7;
	public static final int TIMER = 100;


	
	/* Variables */
	
	
	// pointers to the level before and after this level.
	Level prev,next; 
	
	Portal portalLeft, portalRight;
	Player player; 
	Payload payload;
	private Timer timer;
	private boolean payloadRetrieved;

	
	public Level(MainApplication app, String levelType) {
		super();
		program = app;
		player = new Player();
		payload = new Payload();
		prev = null;
		next = null;
		if (levelType.equals("first")) {
			portalRight = new Portal("right"); 
			portalLeft = null; 
		} else if (levelType.equals("last")) {
			portalRight = null; 
			portalLeft = new Portal("left"); 
		} else {
			portalRight = new Portal("right"); // create rightmost portal
			portalLeft = new Portal("left"); // create leftmost portal
		}
	
		timer = new Timer(TIMER, this);

		payloadRetrieved = false;
		//movement.start();
	}
	
	public void setPrev(Level previous) {
		this.prev = previous;
	}
	
	public void setNext(Level next) {
		this.next = next;
	}
	
	/**********************
	 * KEY AND MOUSE LISTENER METHODS
	 * @param e
	 **********************/
	@Override
	public void keyPressed(KeyEvent e) {
		//Key Press Left or 'A'
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.move(-1 * VELOCITY, 0);
		}
		//Key Press Right or 'D'
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.move(VELOCITY, 0);
		}
		//Key Press Up or 'W'
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			player.move(0, -1 *VELOCITY);
		}
		//Key Press Down or 'S'
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.move(0, VELOCITY);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CollisionChecker.collisions(this, player,portalLeft, portalRight, payload,payloadRetrieved);
		
	}
	
	
	public void startTimer() {
		timer.start();
	}
	
	/**********************
	 * WINDOW SWITCHING AND HIDING
	 **********************/
	@Override
	public void showContents() {
		
		program.add(player.getImage());
		
		if (portalLeft != null) {
			program.add(portalLeft.getImage());
		}
		if (portalRight != null) {
		program.add(portalRight.getImage());
		}
		if (!(payloadRetrieved)) {
			program.add(payload.getImage());
		}
	}
	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	/* switches to the next level. If movingRight is true, switch to the next level.
	 * Otherwise switch to the previous level.  */
	public void switchScreen(boolean movingRight) {
		// stop the timer for this level
		timer.stop();
		// switch screens
		program.switchLevel(movingRight);
	}
	
	// removes the payload and sets payloadRetrieved to true
	public void removePayload() {
		program.remove(payload.getImage());
		payloadRetrieved = true;
	}
	
	public Level getNext() {
		return next;
	}
	
	public boolean payloadRetrieved() {
		return payloadRetrieved;
	}
}
