package game;

import java.awt.*;
import acm.graphics.*;
import starter.MainApplication;
import utility.CollisionHandler;

public class Payload {
	public static final int PAYLOAD_HEIGHT = 50;
	public static final int PAYLOAD_WIDTH = 60;
	public static final int PAYLOAD_START_W = 600;
	
	private Payload nextLoad;
	private Robot image;
	

	// Default Constructor
	public Payload(Color c) {
		this.image = new Robot(PAYLOAD_START_W, MainApplication.centerHeight(PAYLOAD_HEIGHT)/2, PAYLOAD_WIDTH, PAYLOAD_HEIGHT, c);
		this.nextLoad = null;
	}

	// Overloaded Constructor
	public Payload(int startX, int startY, int payloadWidth, int payloadHeight, Color c) {
		this.image = new Robot(startX, startY, payloadWidth, payloadHeight, c); //HEY THIS NEEDS TO BE CHANGED BEFORE YOU COMMIT
		this.nextLoad = null;
	}

	// Moves GImage
	public void moveTo(double x, double y, int dir, int extra, double gap) {
		this.image.setLocation(x - (extra * this.image.getWidth()), y - (this.image.getHeight() / 2));
		if (nextLoad != null) {
			double nextX = CollisionHandler.getCenter(this.image).getX() + (dir * ((this.image.getWidth() / 2) + gap));
			double nextY = CollisionHandler.getCenter(this.image).getY();
			nextLoad.moveTo(nextX, nextY, dir, extra, gap);
		}
	}

	// Returns payload image
	public GObject getImage() {
		return this.image;
	}
	
	public boolean addPayload(Payload add) {
		if (nextLoad != null || add == this) {
			return false;
		}
		nextLoad = add;
		return true;
	}
}