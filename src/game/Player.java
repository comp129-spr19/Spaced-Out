package game;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;
import starter.MainApplication;
import utility.CollisionHandler;

public class Player {
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int VELOCITY = 10;
	public static final double TAILING_GAP = 10;

	private ArrayList<Payload> collectedPayload;
	private Payload firstLoad;
	private GImage image;
	private int direction = 1;

	// Default Constructor
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

	// Sets player image
	public void setImage(GObject character) {
		this.image = (GImage) character;
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
			for (Payload load : collectedPayload) {
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