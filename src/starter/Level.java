package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import game.CollisionChecker;
import game.Payload;
import game.Player;
import game.Portal;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class Level extends GraphicsPane implements ActionListener {
	/*************
	 * VARIABLES *
	 *************/
	private MainApplication program;

	/* CONSTANTS */
	public static final int ASPECT_RATIO = 6;
	public static final int END_GAME_TIMER = 3000;
	public static final int SPAWN_CHAR_LEFT_PORTAL_X = 100;
	public static final int SPAWN_CHAR_RIGHT_PORTAL_X = 700;
	public static final int TBOX_SIZE_Y = MainApplication.WINDOW_HEIGHT / ASPECT_RATIO;
	public static final int TBOX_SIZE_X = MainApplication.WINDOW_WIDTH;
	public static final int TIMER = 100;

	/* PRIVATE VARIABLES */
	private int callStack;
	private Timer timer;
	private GButton stackLabel;
	private GButton[] stackBricks;
	private GImage background = new GImage("r2l_fast.gif", 0, 0);
	private GImage endScreen = new GImage("hyperspace-optimized.gif", 0, 0);
	private GParagraph psuedocode;
	private GRect topMatte, bottomMatte;
	private boolean first, last = false;
	private boolean payloadRetrieved;
	private boolean isMovingLeft;

	/* CLASS VARIABLES */
	Level prev, next; // pointers to the level before and after this level.
	Payload payload;
	Player player;
	Portal portalLeft, portalRight;

	/****************
	 * CONSTRUCTORS *
	 ****************/
	public Level(MainApplication app, String levelType, int stack, int totalLevels) {
		super();
		program = app;

		/* GRAPHICS INITIALIZATION SECTION */
		background.setSize(app.WINDOW_WIDTH, app.WINDOW_HEIGHT);
		background.sendBackward();
		bottomMatte = new GRect(0, app.WINDOW_HEIGHT - (app.WINDOW_HEIGHT / ASPECT_RATIO), app.WINDOW_WIDTH,
				app.WINDOW_HEIGHT / ASPECT_RATIO + app.WINDOW_EXTENSION);
		topMatte = new GRect(0, 0, app.WINDOW_WIDTH, app.WINDOW_HEIGHT / ASPECT_RATIO);
		formatMatte(topMatte, stack);
		formatMatte(bottomMatte, stack);
		player = new Player();
		endScreen.setSize(app.WINDOW_WIDTH, app.WINDOW_HEIGHT + app.WINDOW_EXTENSION);
		psuedocode = new GParagraph(
				"PurpleRobot collectPurpleRobot(Dimension) {\n" + "    if (BasePurple >= CurrentDimensionColor) {\n"
						+ "        return (collectPurpleRobot(ThisDimension + 1) + ThisRobot);\n" + "    } else {\n"
						+ "        return (ThisRobot);\n" + "    }\n" + "}",
				15, app.WINDOW_HEIGHT - (app.WINDOW_HEIGHT / ASPECT_RATIO) + 35);
		psuedocode.setColor(Color.WHITE);
		psuedocode.setFont("Arial-26");

		/* PORTAL SET UP */
		this.callStack = stack;
		if (levelType.equals("first")) {
			portalRight = new Portal("right");
			portalLeft = null;
			first = true;
		} else if (levelType.equals("last")) {
			portalRight = null;
			portalLeft = new Portal("left");
			last = true;
		} else {
			portalRight = new Portal("right"); // create right-most portal
			portalLeft = new Portal("left"); // create left-most portal
		}

		payload = new Payload(levelColor(this.callStack));
		prev = null;
		next = null;

		/* NUMBER BLOCKS INITIAZLIZATION */
		stackLabel = new GButton("Stack:", 0.0, 0.0, app.WINDOW_HEIGHT / ASPECT_RATIO, app.WINDOW_HEIGHT / ASPECT_RATIO,
				levelColor(stack), Color.WHITE);
		stackBricks = new GButton[stack];
		initStackBricks();

		/* TIMER */
		timer = new Timer(TIMER, this);

		/* BOOLEAN INITIALIZATION */
		payloadRetrieved = false;
		isMovingLeft = false;
	}

	/************************
	 * KEY LISTENER METHODS *
	 ************************/
	// Logic provided for <-,^,->,v keys when pressed
	@Override
	public void keyPressed(KeyEvent e) {
		// Key Press Left
		if (e.getKeyCode() == KeyEvent.VK_LEFT && (player.getImage().getX() - 1 >= 0)) {
			isMovingLeft = true;
			player.getImage().setImage("LeftShipStationary.png");
			player.move(-1, 0);
		}
		// Key Press Right
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				&& ((player.getImage().getX() + player.getImage().getWidth()) + 1 <= MainApplication.WINDOW_WIDTH)) {
			isMovingLeft = false;
			player.getImage().setImage("FrontShipStationary.png");
			player.move(1, 0);
		}
		// Key Press Up
		if (e.getKeyCode() == KeyEvent.VK_UP
				&& (player.getImage().getY() - 1 >= (topMatte.getY() + topMatte.getHeight()))) {
			if (isMovingLeft) {
				player.getImage().setImage("leftfacing_movingup.png");
			} else {
				player.getImage().setImage("frontfacing_movingup.png");
			}
			player.move(0, -1);

		}
		// Key Press Down
		if (e.getKeyCode() == KeyEvent.VK_DOWN && ((player.getImage().getY() + player.getImage().getHeight())
				+ 1 <= (MainApplication.WINDOW_HEIGHT - (MainApplication.WINDOW_HEIGHT / ASPECT_RATIO)))) {
			if (isMovingLeft) {
				player.getImage().setImage("leftfacing_movingdown.png");
			} else {
				player.getImage().setImage("frontfacing_movingdown.png");
			}
			player.move(0, 1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Key Released Up Or Down
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (isMovingLeft) {
				player.getImage().setImage("LeftShipStationary.png");
			} else {
				player.getImage().setImage("FrontShipStationary.png");
			}
		}
	}

	/****************************
	 * ACTION PERFORMED METHODS *
	 ****************************/
	// Collision checker
	@Override
	public void actionPerformed(ActionEvent e) {
		CollisionChecker.collisions(this, player, portalLeft, portalRight, payload, payloadRetrieved);
	}

	// Starts the timer for
	public void startTimer() {
		timer.start();
	}

	/*******************************
	 * WINDOW SWITCHING AND HIDING *
	 *******************************/
	// Shows the contents of this pane by adding all graphical elements
	@Override
	public void showContents() {
		program.add(background);
		program.add(topMatte);
		program.add(bottomMatte);
		program.add(stackLabel);
		for (int i = 0; i < callStack; i++) {
			program.add(stackBricks[i]);
		}

		program.add(player.getImage());
		player.move(0, 0);

		AudioPlayer.getInstance().playSound("sounds", "LevelMusic.mp3", true);
		if (portalLeft != null) {
			program.add(portalLeft.getImage());
		}
		if (portalRight != null) {
			program.add(portalRight.getImage());
		}
		if (next == null || next.payloadRetrieved) {
			program.add(payload.getImage());
		}

		ArrayList<Payload> collectedPayload = player.getPayloads();

		program.add(psuedocode);

		for (int i = 0; i < collectedPayload.size(); i++) {
			program.add(collectedPayload.get(i).getImage());
		}
	}

	// Hides the contents of this pane by removing all graphical elements
	@Override
	public void hideContents() {
		program.removeAll();
	}

	/*
	 * Level Switching Logic: -If movingRight() method is true, then switches to the
	 * next level. -Else switch to the previous level.
	 */
	public void switchScreen(boolean movingRight) {
		// stop the timer for this level
		timer.stop();
		// switch screens
		if (movingRight) {

			next.setPlayer(this.player);
			next.getPlayer().respawnTo(SPAWN_CHAR_LEFT_PORTAL_X,
					MainApplication.centerHeight(next.getPlayer().getHeight()));

			next.changeLeftPortal(false);

		} else {

			prev.setPlayer(this.player);
			prev.getPlayer().respawnTo(SPAWN_CHAR_RIGHT_PORTAL_X,
					MainApplication.centerHeight(prev.getPlayer().getHeight()));

			// prev.changeLeftPortal(true);
			prev.changeRightPortal(false);
		}
		program.switchLevel(movingRight);
	}

	// methods to switch to the end screen without adding a new pane
	private void switchToEndScreen() {
		program.removeAll();
		program.add(endScreen);
		AudioPlayer.getInstance().playSound("sounds", "HyperSpaceSound.mp3");
	}

	/***********************
	 * SETTERS AND GETTERS *
	 ***********************/
	// Sets the player by saving its current state
	public void setPlayer(Player player) {
		this.player = player;
	}

	// Sets the previous level pointer
	public void setPrev(Level previous) {
		this.prev = previous;
	}

	// Sets the next level pointer
	public void setNext(Level next) {
		this.next = next;
	}

	// Gets Player from player class
	public Player getPlayer() {
		return player;
	}

	// Gets the next level
	public Level getNext() {
		return next;
	}

	// Gets the value in call stack
	public int getCallStack() {
		return this.callStack;
	}

	// Gets Payload's TRUE/FALSE flag value
	public boolean payloadRetrieved() {
		return payloadRetrieved;
	}

	// Gets Payload's First value
	public boolean isFirst() {
		return first;
	}

	// Gets Payload's Last flag value
	public boolean isLast() {
		return last;
	}

	/******************
	 * HELPER METHODS *
	 ******************/
	/*
	 * PAYLOAD AQUISITION LOGIC: -Plays R2-D2 sound after picking up each payload
	 * -addPayLoad(): adds the robot to the back of the ship -If it's the first
	 * robot on the stack, or last picked up then starts Game Complete sequence
	 * -Else displays label of next event
	 */
	public void removePayload() {
		// Plays R2-D2 sounds
		AudioPlayer.getInstance().playSound("sounds", "r2d2.mp3", false);
		// Adds robot to the back of the ship
		player.addPayload(payload);
		// Starts Game Complete Sequence
		if (isFirst()) {
			AudioPlayer.getInstance().stopSound("sounds", "LevelMusic.mp3");
			// AudioPlayer.getInstance().stopSound("sounds", "r2d2.mp3");
			AudioPlayer.getInstance().playSound("sounds", "game_complete.mp3", false);
			switchToEndScreen();
			// Displays next label
		} else {
			changeLeftPortal(true);
		}
		// Turns Payload flag to true
		payloadRetrieved = true;
	}

	// GRAPHICS METHOD: Formats Wide Screen Matte
	public void formatMatte(GRect panel, int level) {
		panel.setColor(levelColor(level));
		panel.setFilled(true);
		panel.setFillColor(levelColor(level));
	}

	// GRAPHICS METHOD: Creates the 'Blocks with numbers on them' on top left side
	// of screen
	public void initStackBricks() {
		double brickSize = MainApplication.WINDOW_HEIGHT / ASPECT_RATIO;
		// Array of numbered blocks, also formats them
		for (int i = 0; i < callStack; i++) {
			stackBricks[i] = new GButton(String.valueOf(i + 1), (i + 1) * brickSize, 0.0, brickSize, brickSize,
					levelColor(i + 1), Color.WHITE);
		}
	}

	// returns the color of the current level
	public Color levelColor(int levelNumber) {

		// value for which we'll multiply the blueness of each level
		int blueIncrementValue = 51;

		// play around with these values to get a desirable color
		int redVal = 120;
		int greenVal = 0;

		return new Color(redVal, greenVal, blueIncrementValue * levelNumber);
	}

	// given a GObject, change its color.
	public void setObjectColor(GObject object, Color color) {
		object.setColor(color);
	}

	public void changeLeftPortal(boolean toActive) {
		if (portalLeft != null) {
			if (toActive) {
				portalLeft.setImage("Portal.gif");
			} else {
				portalLeft.setImage("PortalStill.png");
			}
		}
	}

	public void changeRightPortal(boolean toActive) {
		if (portalRight != null) {
			if (toActive) {
				portalRight.setImage("Portal.gif");
			} else {
				portalRight.setImage("PortalStill.png");
			}
		}
	}
}
