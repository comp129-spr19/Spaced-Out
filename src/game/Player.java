package game;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;
import starter.MainApplication;
import utility.CollisionHandler;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class Player {
	/***********
	 * VARIABLES
	 ***********/
	
	/* CONSTANTS */
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int VELOCITY = 10;
	public static final double TAILING_GAP = 10;

	/* PRIVATE VARIABLES */
	private ArrayList<Payload> collectedPayload;
	private Payload firstLoad;
	private GImage image;
	private int direction = 1;

	/****************
	 * CONSTRUCTORS *
	 ****************/
	public Player() {
		this.image = new GImage("FrontShipStationary.png");
		this.image.setLocation(PLAYER_START_W, (MainApplication.WINDOW_HEIGHT / 2) - (this.image.getHeight() / 2));
		this.collectedPayload = new ArrayList<Payload>();
		firstLoad = null;
	}

	// Overloaded Constructor
	public Player(int startX, int startY, int playerWidth, int playerHeight) {
		this.image = new GImage("FrontShipStationary.png", startX, startY);
		image.setLocation(startX, startY);
	}

	/***********************
	 * SETTERS AND GETTERS *
	 ***********************/	
	// Gets player image
	public GImage getImage() {
		return this.image;
	}
	
	// Gets array of collected payloads
	public ArrayList<Payload> getPayloads() {
		return collectedPayload;
	}
	
	//Gets Player height
	public int getHeight() {
		return PLAYER_SIZE;
	}

	/******************
	 * HELPER METHODS *
	 ******************/
	// Moves GImage
	public void move(int x, int y) {
		this.image.move(x * VELOCITY, y * VELOCITY);
		if (firstLoad != null) {
			if (x != 0) {
				direction = -1 * x;
			}
			int extraGap = 1;
			if (direction >= 0) {
				extraGap = 0;
			}
			double nextX = CollisionHandler.getCenter(image).getX()
					+ (direction * ((image.getWidth() / 2) + TAILING_GAP));
			double nextY = CollisionHandler.getCenter(image).getY();
			firstLoad.moveTo(nextX, nextY, direction, extraGap, TAILING_GAP);
		}
	}
	
	// Adds Payload object on back of the ship
	public void addPayload(Payload add) {
		collectedPayload.add(add);
		if (firstLoad == null) {
			firstLoad = add;
		} else {
			for (Payload load : collectedPayload) {
				if (load.addPayload(add)) {
					break;
				}
			}
		}
	}

	//  Respawns to a specific location
	public void respawnTo(double x, double y) {
		this.image.setLocation(x, y);
	}
}