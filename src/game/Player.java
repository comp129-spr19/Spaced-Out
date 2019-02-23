package game;

import java.util.*;
import acm.graphics.*;
import starter.MainApplication;
import utility.CollisionHandler;

public class Player {
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int VELOCITY = 7;
	public static final double TAILING_GAP = 10;
	
	private ArrayList<Payload> collectedPayload;
	private Payload firstLoad;
	private GOval image;
	private int direction = 1;

	// Default Constructor
	public Player() {
		this.image = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		image.setFilled(true);
		this.collectedPayload = new ArrayList<Payload>();
		firstLoad = null;
	}

	// Overloaded Constructor
	public Player(int startX, int startY, int playerWidth, int playerHeight) {
		this.image = new GOval(startX, startY, playerWidth, playerHeight);
	}

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
			double nextX = CollisionHandler.getCenter(image).getX() + (direction * ((image.getWidth() / 2) + TAILING_GAP));
			double nextY = CollisionHandler.getCenter(image).getY();
			firstLoad.moveTo(nextX, nextY, direction, extraGap, TAILING_GAP);
		}
	}

	// Sets player image
	public void setImage(GObject character) {
		this.image = (GOval) character;
	}

	// Returns player image
	public GObject getImage() {
		return this.image;
	}
	
	public void addPayload(Payload add) {
		collectedPayload.add(add);
		if (firstLoad == null) {
			firstLoad = add;
		} else {
			for (Payload load: collectedPayload) {
				if (load.addPayload(add)) {
					break;
				}
			}
		}
	}
	
	public void respawnTo(double x, double y) {
		this.image.setLocation(x, y);
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return PLAYER_SIZE;
	}

	public ArrayList<Payload> getPayloads() {
		// TODO Auto-generated method stub
		return collectedPayload;
	}

}