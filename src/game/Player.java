package game;

import java.util.ArrayList;

import acm.graphics.GOval;
import starter.MainApplication;

public class Player {
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
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
		this.image.move(x, y);
	}

	// Sets player image
	public void setImage(GOval character) {
		this.image = character;
	}

	// Returns player image
	public GOval getImage() {
		return this.image;
	}

}