package game;

import java.util.*;
import acm.graphics.*;
import starter.MainApplication;

public class Player {
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int VELOCITY = 7;
	public static final int TAILING_GAP = 15;
	
	private ArrayList<Payload> collectedPayload;
	private GOval image;

	// Default Constructor
	public Player() {
		this.image = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		image.setFilled(true);
		this.collectedPayload = new ArrayList<Payload>();
	}

	// Overloaded Constructor
	public Player(int startX, int startY, int playerWidth, int playerHeight) {
		this.image = new GOval(startX, startY, playerWidth, playerHeight);
	}

	// Moves GImage
	public void move(int x, int y) {
		this.image.move(x * VELOCITY, y * VELOCITY);
		double lastX = image.getX() + image.getWidth() + TAILING_GAP;
		double lastY = image.getY();
		for (Payload load: collectedPayload) {
			load.moveTo(lastX, lastY);
			lastX = load.getImage().getX() + load.getImage().getWidth() + TAILING_GAP;
			lastY = load.getImage().getY();
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
	}
	
	public void respawnTo(double x, double y) {
		this.image.setLocation(x, y);
		this.move(0, 0);
	}
	
	public int getHeight() {
		return PLAYER_SIZE;
	}

}