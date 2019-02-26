package game;

import java.awt.*;
import acm.graphics.*;
import starter.MainApplication;
import utility.CollisionHandler;

public class Payload {
	public static final int PAYLOAD_HEIGHT = 50;
	public static final int PAYLOAD_WIDTH = 60;
	public static final int PAYLOAD_START_W = 600;
	public static final String IMAGE_FILENAME = "robot.gif";
	
	private Payload nextLoad;
	private GImage image;
	

	// Default Constructor
	public Payload() {
		this.image = new GImage(IMAGE_FILENAME, PAYLOAD_START_W, MainApplication.centerHeight(PAYLOAD_HEIGHT)/2);
		image.setSize(PAYLOAD_WIDTH, PAYLOAD_HEIGHT);
		this.nextLoad = null;
	}

	// Overloaded Constructor
	public Payload(int startX, int startY, int payloadWidth, int payloadHeight) {
		this.image = new GImage(IMAGE_FILENAME, startX, startY);
		image.setSize(payloadWidth, payloadHeight);
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

	// Sets payload image
	public void setImage(GImage payload) {
		this.image = payload;
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