package starter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import game.CollisionChecker;
import game.Payload;
import game.Player;
import game.Portal;
import utility.*;

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
	public static final int TBOX_SIZE_Y = MainApplication.WINDOW_HEIGHT/ASPECT_RATIO;
	public static final int TBOX_SIZE_X = MainApplication.WINDOW_WIDTH;
	public static final int TIMER = 100;
	
	/* DIALOGUE*/
	private static final String BASE_DIMENSION_DIALOGUE = "YOU ARE AT THE HIGHEST DIMENSION, RETRIEVE THE FIRST ROBOT!";
	private static final String MISSION_COMPLETE = "MISSION COMPLETE: SUCCESSFULLY RETRIEVED ALL THE ROBOTS";
	private static final String NEW_OBJ = "OBJECTIVE: Retrieve the robot from the previous dimension!";
	private static final String RET_FINAL_ROB = "RETRIEVE THE FINAL ROBOT!";
	private static final String RETRIEVE_ROB = "OBJETIVE: Retrive this dimension's robot!";
	
	/* PRIVATE VARIABLES */
	private int callStack;
	private Timer timer;
	private GButton[] stackBricks;
	private GImage background = new GImage("r2l_fast.gif", 0, 0);
	private GImage endScreen = new GImage("hyperspace-optimized.gif",0,0);
	private GLabel dialogueBox;
	private GRect topMatte, bottomMatte;
	private boolean first, last = false;
	private boolean payloadRetrieved;
	
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
		/*    GRAPHICS INITIALIZATION SECTION - AND STACK ORDER 
		 * 1: Background -> bottom of stack | send backward
		 * 2: Matte | send to back
		 * 3: Player | send to front
		 * 4: Payload | send to front
		 * 5: Portals | send forward
		 * 6: Dialogue Box -> top of the stack | send forward
		 * 7: End Screen | removeAll then add */
		background.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		background.sendBackward();
		bottomMatte = new GRect(0, app.WINDOW_HEIGHT - (app.WINDOW_HEIGHT / ASPECT_RATIO), app.WINDOW_WIDTH,
				      app.WINDOW_HEIGHT / ASPECT_RATIO);
		topMatte = new GRect(0, 0, app.WINDOW_WIDTH, app.WINDOW_HEIGHT / ASPECT_RATIO);
		formatMatte(topMatte);
		formatMatte(bottomMatte);
		player = new Player();
		dialogueBox = new GLabel(FileReader.readWholeFile("OBJECTIVE_ONE.txt")); // adding GLabels in the bottom
		dialogueBox.setColor(Color.WHITE);
		dialogueBox.setLocation(0, MainApplication.WINDOW_HEIGHT - dialogueBox.getHeight());
		endScreen.setSize(app.WINDOW_WIDTH, app.WINDOW_HEIGHT);
		
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
		
		/* LEVEL POINTER  INTIALIZATION */
		prev = null;
		next = null;
		
		/* NUMBER BLOCKS INITIAZLIZATION */
		stackBricks = new GButton[stack];
		initStackBricks();
		
		/* TIMER */
		timer = new Timer(TIMER, this);
		
		/* BOOLEAN INITIALIZATION */
		payloadRetrieved = false;
	}

	/************************
	 * KEY LISTENER METHODS *
	 ************************/
	// Logic provided for <-,^,->,v keys and AWDS keys when pressed
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
		program.add(dialogueBox);
		dialogueBox.sendToFront();
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
		if (next == null || next.payloadRetrieved) {
			program.add(payload.getImage());
		}

		ArrayList<Payload> collectedPayload = player.getPayloads();

		for (int i = 0; i < collectedPayload.size(); i++) {
			program.add(collectedPayload.get(i).getImage());
		}
	}

	// Hides the contents of this pane by removing all graphical elements
	@Override
	public void hideContents() {
		program.removeAll();
	}

	/*  Level Switching Logic: 
	 *  -If movingRight() method is true, then switches to the next level.
	 *  -Else switch to the previous level. */
	public void switchScreen(boolean movingRight) {
		// stop the timer for this level
		timer.stop();
		// switch screens
		if (movingRight) {
			next.setPlayer(this.player);
			next.getPlayer().respawnTo(SPAWN_CHAR_LEFT_PORTAL_X,
					MainApplication.centerHeight(next.getPlayer().getHeight()));
			if(next.isLast()) {
				next.setDialogue(BASE_DIMENSION_DIALOGUE);
			}

		} else {
			prev.setPlayer(this.player);
			prev.getPlayer().respawnTo(SPAWN_CHAR_RIGHT_PORTAL_X,
					MainApplication.centerHeight(prev.getPlayer().getHeight()));
			if (prev.isFirst()) {
				prev.setDialogue(RET_FINAL_ROB);
			} else {
				prev.setDialogue(RETRIEVE_ROB);
			}
		}
		program.switchLevel(movingRight);
	}
	
	//methods to switch to the end screen without adding a new pane
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
	
	//Gets Player from player class
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
/*  PAYLOAD AQUISITION LOGIC:
	 *  -Plays  R2-D2 sound after picking up each payload
	 *  -addPayLoad(): adds the robot to the back of the ship
	 *  -If it's the first robot on the stack, or last picked up then starts Game Complete sequence 
	 *  -Else displays label of next event */
	public void removePayload() {
		// Plays R2-D2 sounds
		AudioPlayer.getInstance().playSound("sounds", "r2d2.mp3", false);
		// Adds robot to the back of the ship
		player.addPayload(payload);
		// Starts Game Complete Sequence
		if (isFirst()) {
			dialogueBox.setLabel(MISSION_COMPLETE);
			AudioPlayer.getInstance().stopSound("sounds", "LevelMusic.mp3");
			//AudioPlayer.getInstance().stopSound("sounds", "r2d2.mp3");
			AudioPlayer.getInstance().playSound("sounds", "game_complete.mp3",false);
			switchToEndScreen();
		// Displays next label	
		} else {
		dialogueBox.setLabel(NEW_OBJ);
		}
		// Turns Payload flag to true 
		payloadRetrieved = true;
	}
	
	// GRAPHICS METHOD: Formats Wide Screen Matte
	public void formatMatte(GRect panel) {
		panel.setColor(Color.BLACK);
		panel.setFilled(true);
		panel.setFillColor(Color.BLACK);
	}

	// GRAPHICS METHOD: Creates the 'Blocks with numbers on them' on top left side of screen
	public void initStackBricks() {
		double brickSize = MainApplication.WINDOW_HEIGHT / ASPECT_RATIO;
		// Array of numbered blocks, also formats them
		for (int i = 0; i < callStack; i++) {
			stackBricks[i] = new GButton(String.valueOf(i + 1), i * brickSize, 0.0, brickSize, brickSize,
					levelColor(i + 1));
		}
	}
		
	// Dialogue changer
	public void setDialogue(String dialogue) {
		dialogueBox.setLabel(dialogue);
	}
	
	
	// returns the color of the current level
	public Color levelColor(int levelNumber) {
		
		// value for which we'll multiply the blueness of each level
		int blueIncrementValue = 51;
		
		// play around with these values to get a desirable color
		int redVal = 120;
		int greenVal = 0;
		
		return new Color(redVal,greenVal,blueIncrementValue * levelNumber);
	}
	
	// given a GObject, change its color. 
	public void setObjectColor (GObject object, Color color) {
		object.setColor(color);
	}
}
