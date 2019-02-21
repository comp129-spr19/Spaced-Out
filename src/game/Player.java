package game;

import java.util.ArrayList;

import acm.graphics.GOval;
import starter.MainApplication;

public class Player {
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	private Payload[] collectedPayload;
	private GOval character;

	// Default Constructor
	public Player() {
		this.character = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		this.collectedPayload = new ArrayList<Payload>();
	}

	// Overloaded Constructor
	public Player(int startX, int startY, int playerWidth, int playerHeight) {
		this.character = new GOval(startX, startY, playerWidth, playerHeight);

	}

	// Moves GImage
	void move(int x, int y) {
		this.character.move(x, y);
	}

	// Sets player image
	void setPlayer(GOval character) {
		this.character = character;
	}

	// Returns player image
	GOval getPlayer() {
		return this.character;
	}

}