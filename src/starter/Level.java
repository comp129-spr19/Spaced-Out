package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import game.CollisionChecker;
import game.Payload;
import game.Player;
import game.Portal;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class Level extends GraphicsPane implements ActionListener {

	/* CONSTANTS */
	private MainApplication program; // use this 'program.something' for all program calls

	public static final int SPAWN_CHAR_LEFT_PORTAL_X = 100;
	public static final int SPAWN_CHAR_RIGHT_PORTAL_X = 700;

	public static final int TIMER = 100;

	/* Variables */
	private int callStack;

	// pointers to the level before and after this level.
	Level prev, next;

	Portal portalLeft, portalRight;
	Player player;
	Payload payload;
	private Timer timer;
	private boolean payloadRetrieved;

	// creates a new level. Differentiates between first and last level
	// based on the string provided
	public Level(MainApplication app, String levelType, int stack) {
		super();
		program = app;
		player = new Player();
		payload = new Payload();
		prev = null;
		next = null;
		this.callStack = stack;
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
		// movement.start();
	}

	// sets the previous level pointer
	public void setPrev(Level previous) {
		this.prev = previous;
	}

	// sets the next level pointer
	public void setNext(Level next) {
		this.next = next;
	}

	/**********************
	 * KEY AND MOUSE LISTENER METHODS
	 * 
	 * @param e
	 **********************/
	@Override
	public void keyPressed(KeyEvent e) {
		// Key Press Left or 'A'
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.move(-1, 0);
		}
		// Key Press Right or 'D'
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.move(1, 0);
		}
		// Key Press Up or 'W'
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			player.move(0, -1);
		}
		// Key Press Down or 'S'
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.move(0, 1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CollisionChecker.collisions(this, player, portalLeft, portalRight, payload, payloadRetrieved);

	}

	// start the internal timer
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

		ArrayList<Payload> collectedPayload = player.getPayloads();

		for (int i = 0; i < collectedPayload.size(); i++) {
			program.add(collectedPayload.get(i).getImage());
		}
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}

	/*
	 * switches to the next level. If movingRight is true, switch to the next level.
	 * Otherwise switch to the previous level.
	 */
	public void switchScreen(boolean movingRight) {
		// stop the timer for this level
		timer.stop();
		// switch screens
		if (movingRight) {
			next.setPlayer(this.player);
			next.getPlayer().respawnTo(SPAWN_CHAR_LEFT_PORTAL_X,
					MainApplication.centerHeight(next.getPlayer().getHeight()));

		} else {
			prev.setPlayer(this.player);
			prev.getPlayer().respawnTo(SPAWN_CHAR_RIGHT_PORTAL_X,
					MainApplication.centerHeight(prev.getPlayer().getHeight()));
		}
		program.switchLevel(movingRight);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	// removes the payload and sets payloadRetrieved to true
	public void removePayload() {
		player.addPayload(payload);
		// this is a comment because I broke git
		payloadRetrieved = true;
	}

	// return the next level
	public Level getNext() {
		return next;
	}

	// check if pay load has been retrieved
	public boolean payloadRetrieved() {
		return payloadRetrieved;
	}

	// returns the value in call stack
	public int getCallStack() {
		return this.callStack;
	}
}
