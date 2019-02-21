package game;

import acm.graphics.GRect;
import starter.MainApplication;

public class Payload {
	public static final int PAYLOAD_HEIGHT = 50;
	public static final int PAYLOAD_WIDTH = 60;
	public static final int PAYLOAD_START_W = 600;
	private GRect image;

	// Default Constructor
	public Payload() {
		this.image = new GRect(PAYLOAD_START_W, MainApplication.centerHeight(PAYLOAD_HEIGHT)/2, PAYLOAD_WIDTH,
				PAYLOAD_HEIGHT);
	}

	// Overloaded Constructor
	public Payload(int startX, int startY, int payloadWidth, int payloadHeight) {
		this.image = new GRect(startX, startY, payloadWidth, payloadHeight);
	}

	// Moves GImage
	public void move(int x, int y) {
		this.image.move(x, y);
	}

	// Sets payload image
	public void setImage(GRect payload) {
		this.image = payload;
	}

	// Returns payload image
	public GRect getImage() {
		return this.image;
	}
}