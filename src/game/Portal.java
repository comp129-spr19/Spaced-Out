package game;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GOval;
import starter.MainApplication;

// this class represents a portal object in the game
public class Portal {

	// DIMENSIONS AND COORDINATES FOR THE PORTAL
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_LEFT_START_W = 100;
	public static final int PORTAL_RIGHT_START_W = 700;
	
	
	// private variables
	
	private GImage image;
	
	// if specified will create a right portal, otherwise will just create a left portal
	public Portal(String portalType) {
		if (portalType.equals("right")) {
		image = new GImage("BlackHolePortal.gif");
		image.setSize(PORTAL_WIDTH, PORTAL_HEIGHT);
		image.setLocation(PORTAL_RIGHT_START_W, MainApplication.WINDOW_HEIGHT / 2 - image.getHeight() / 2 );
		} else if (portalType.equals("left")) {
		image = new GImage("BlackHolePortal.gif");
		image.setSize(PORTAL_WIDTH, PORTAL_HEIGHT);
		image.setLocation(PORTAL_LEFT_START_W, MainApplication.WINDOW_HEIGHT / 2 - image.getHeight() / 2 );
		}
	}
	
	public GImage getImage() {
		return image;
	}
	
	public void setImage(GImage newImage) {
		image = newImage;
	}
}
