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
		x = -1 * x;
		y = -1 * y;
		double lastX = (image.getX() + (image.getWidth() / 2)) + (x * ((image.getWidth() / 2) + TAILING_GAP));
		double lastY = CollisionHandler.getCenter(image).getY() + (y * ((image.getHeight() / 2) + TAILING_GAP));
		for (Payload load: collectedPayload) {
			GObject loadImage = load.getImage();
			load.moveTo(lastX - (loadImage.getWidth() / 2), lastY - (loadImage.getHeight() / 2));
			lastX += x * (loadImage.getWidth() + TAILING_GAP);
			lastY += y * (loadImage.getHeight() + TAILING_GAP);
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
	}

}