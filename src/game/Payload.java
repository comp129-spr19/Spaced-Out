package game;

import acm.graphics.GRect;
import starter.MainApplication;

public class Payload {
	public static final int PAYLOAD_HEIGHT = 50;
	public static final int PAYLOAD_WIDTH = 60;
	public static final int PAYLOAD_START_W = 600;
	private GRect payload;

	// Default Constructor
	public Payload() {
		this.payload = new GRect(PAYLOAD_START_W, MainApplication.centerHeight(PAYLOAD_HEIGHT), PAYLOAD_WIDTH,
				PAYLOAD_HEIGHT);
	}

	// Overloaded Constructor
	public Payload(int startX, int startY, int payloadWidth, int payloadHeight) {
		this.payload = new GRect(startX, startY, payloadWidth, payloadHeight);
	}

	// Moves GImage
	void move(int x, int y) {
		this.payload.move(x, y);
	}

	// Sets payload image
	void setPayload(GRect payload) {
		this.payload = payload;
	}

	// Returns payload image
	GRect getPayload() {
		return this.payload;
	}
}