package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
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

	/* CONSTANTS */
	private MainApplication program; // use this 'program.something' for all program calls

	public static final int SPAWN_CHAR_LEFT_PORTAL_X = 100;
	public static final int SPAWN_CHAR_RIGHT_PORTAL_X = 700;
	public static final int TIMER = 100;
	
	public static final int ASPECT_RATIO = 6;
	public static final int TBOX_SIZE_Y = MainApplication.WINDOW_HEIGHT/ASPECT_RATIO;
	public static final int TBOX_SIZE_X = MainApplication.WINDOW_WIDTH;
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
	private GImage background = new GImage("r2l_fast.gif", 0, 0);
	private GRect topMatte, bottomMatte;
	private GLabel dialogueBox;
	private boolean first, last = false;
	//private GLabel currentPower;

	// creates a new level. Differentiates between first and last level
	// based on the string provided
	public Level(MainApplication app, String levelType, int stack, int totalLevels) {
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
			first = true;
		} else if (levelType.equals("last")) {
			portalRight = null;
			portalLeft = new Portal("left");
			last = true;
		} else {
			portalRight = new Portal("right"); // create rightmost portal
			portalLeft = new Portal("left"); // create leftmost portal
		}

		stackBricks = new GButton[stack];
		initStackBricks();

		
		// adding GLabels in the bottom
		dialogueBox = new GLabel(FileReader.readWholeFile("OBJECTIVE_ONE.txt"));
		
		dialogueBox.setColor(Color.WHITE);

		dialogueBox.setLocation(0, MainApplication.WINDOW_HEIGHT - dialogueBox.getHeight());
		
		timer = new Timer(TIMER, this);

		payloadRetrieved = false;
		// movement.start();
	}

	// sets the previous level pointer
	public void setPrev(Level previous) {
		this.prev = previous;
	}

	public int factorial(int n) 
    { 
        if (n == 0) 
          return 1; 
          
        return n*factorial(n-1); 
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
			if(next.isLast()) {
				next.setDialogue("YOU ARE AT THE BASE DIMENSION, RETRIEVE THE FIRST ROBOT!");
			}

		} else {
			prev.setPlayer(this.player);
			prev.getPlayer().respawnTo(SPAWN_CHAR_RIGHT_PORTAL_X,
					MainApplication.centerHeight(prev.getPlayer().getHeight()));
			if (prev.isFirst()) {
				prev.setDialogue("RETRIEVE THE FINAL ROBOT!");
			} else {
				prev.setDialogue("OBJETIVE: Retrive this dimension's robot!");
			}
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
		AudioPlayer.getInstance().playSound("sounds", "r2d2.mp3", false);
		player.addPayload(payload);
		if (isFirst()) {
			dialogueBox.setLabel("MISSION COMPLETE: SUCCESSFULLY RETRIEVED ALL THE ROBOTS");
			AudioPlayer.getInstance().stopSound("sounds", "LevelMusic.mp3");
			AudioPlayer.getInstance().playSound("sounds", "game_complete.mp3",false);
		} else {
		dialogueBox.setLabel("OBJECTIVE: Retrieve the robot from the previous dimension!");
		}
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
	
	// check if this is the first level
	public boolean isFirst() {
		return first;
	}
	
	// check if this is the last level
	public boolean isLast() {
		return last;
	}
	
	// change the dialogue of the level
	public void setDialogue(String dialogue) {
		dialogueBox.setLabel(dialogue);
	}
}
