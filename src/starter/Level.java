package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GRect;
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

	public static final int ASPECT_RATIO = 6;

	/* Variables */
	private int callStack;
	private GButton[] stackBricks;

	// pointers to the level before and after this level.
	Level prev, next;

	Portal portalLeft, portalRight;
	Player player;
	Payload payload;
	private Timer timer;
	private boolean payloadRetrieved;
	private GImage background = new GImage("LevelsBackgroundRightToLeft.gif", 0, 0);
	private GRect topMatte, bottomMatte;

	// creates a new level. Differentiates between first and last level
	// based on the string provided
	public Level(MainApplication app, String levelType, int stack) {
		super();
		program = app;
		background.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		background.sendToBack();

		topMatte = new GRect(0, 0, app.WINDOW_WIDTH, app.WINDOW_HEIGHT / ASPECT_RATIO);
		bottomMatte = new GRect(0, app.WINDOW_HEIGHT - (app.WINDOW_HEIGHT / ASPECT_RATIO), app.WINDOW_WIDTH,
				app.WINDOW_HEIGHT / ASPECT_RATIO);
		formatMatte(topMatte);
		formatMatte(bottomMatte);

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

		stackBricks = new GButton[stack];
		initStackBricks();

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
		if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
				&& (player.getImage().getX() - 1 >= 0)) {
			player.move(-1, 0);
		}
		// Key Press Right or 'D'
		if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
				&& ((player.getImage().getX() + player.getImage().getWidth()) + 1 <= MainApplication.WINDOW_WIDTH)) {
			player.move(1, 0);
		}
		// Key Press Up or 'W'
		if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
				&& (player.getImage().getY() - 1 >= (topMatte.getY() + topMatte.getHeight()))) {
			player.move(0, -1);
		}
		// Key Press Down or 'S'
		if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
				&& ((player.getImage().getY() + player.getImage().getHeight())
						+ 1 <= (MainApplication.WINDOW_HEIGHT - bottomMatte.getHeight()))) {
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
		program.add(background);
		program.add(topMatte);
		program.add(bottomMatte);

		for (int i = 0; i < callStack; i++) {
			program.add(stackBricks[i]);
		}

		program.add(player.getImage());

		AudioPlayer.getInstance().playSound("sounds", "LevelMusic.mp3", true);
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

	// initializes the widescreen matte panels
	public void formatMatte(GRect panel) {
		panel.setColor(Color.BLACK);
		panel.setFilled(true);
		panel.setFillColor(Color.BLACK);
	}

	// initialize the array of stack call bricks
	public void initStackBricks() {
		double brickSize = MainApplication.WINDOW_HEIGHT / ASPECT_RATIO;

		for (int i = 0; i < callStack; i++) {
			stackBricks[i] = new GButton(String.valueOf(i + 1), i * brickSize, 0.0, brickSize, brickSize,
					Color.DARK_GRAY);
		}
	}
}
