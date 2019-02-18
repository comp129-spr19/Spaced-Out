package starter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.graphics.*;
import game.CollisionChecker;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class MenuPane extends GraphicsPane implements ActionListener {
	/**********************
	 * GLOBAL VARIABLES
	 **********************/
	private MainApplication program; //use this 'program.something' for all program calls
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_START_W = 700;
	public static final int VELOCITY = 2;
	public static final int TIMER = 100;
	public static final int DELAY_BEFORE_DETECTING_COLLISIONS = 50;
	private GOval portal;
	private GOval player;
	private GRect payload;
	private Timer movement;
	private boolean payloadGotten;
	
	int numTimeIterations;
	/**********************
	 * CONSTRUCTOR
	 * @param app
	 **********************/	
	public MenuPane(MainApplication app) {
		super();
		program = app;
		player = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		player.setFilled(true);
		portal = new GOval(PORTAL_START_W, MainApplication.centerHeight(PORTAL_HEIGHT), PORTAL_WIDTH, PORTAL_HEIGHT);
		payload = null;
		movement = new Timer(TIMER, this);
		
		// number of iterations have to pass before collision detection.
		// Set at max value currently, since we want to immediately begin collision detection
		//This should be changed with a much more clever solution second sprint. 
		numTimeIterations = DELAY_BEFORE_DETECTING_COLLISIONS;
		payloadGotten = true;
		//movement.start();
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
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == portal) {
			program.switchToSome();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//begin checking collisions after x passes of the timer
		if (numTimeIterations < DELAY_BEFORE_DETECTING_COLLISIONS) {
			numTimeIterations++;
		} else {
		CollisionChecker.collisions(this, player, portal, payload,payloadGotten);
		}
	}
	public void startTimer() {
		movement.start();
	}
	
	/**********************
	 * WINDOW SWITCHING AND HIDING
	 **********************/
	@Override
	public void showContents() {
		program.add(portal);
		program.add(player);
	}
	@Override
	public void hideContents() {
		program.removeAll();
	}
	
	/* switches to the next level */
	public void switchScreen() {
		// stop the timer for this level
		movement.stop();
		
		// set the number of iterations, so we can again experience this delay.
		numTimeIterations = 0;
		// switch screens
		program.switchToSome();
	}
	
	public void removePayload() {
		program.remove(payload);
	}
}
