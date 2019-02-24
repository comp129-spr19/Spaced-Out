package game;

import java.awt.Color;

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
	
	private GOval image;
	
	// if specified will create a right portal, otherwise will just create a left portal
	public Portal(String portalType) {
		if (portalType.equals("right")) {
		image = new GOval(PORTAL_RIGHT_START_W, MainApplication.centerHeight(PORTAL_HEIGHT), PORTAL_WIDTH, PORTAL_HEIGHT);
		} else if (portalType.equals("left")) {
		image = new GOval(PORTAL_LEFT_START_W, MainApplication.centerHeight(PORTAL_HEIGHT), PORTAL_WIDTH, PORTAL_HEIGHT);
		}
		image.setFilled(true);
		image.setFillColor(Color.WHITE);
	}
	
	public GOval getImage() {
		return image;
	}
	
	public void setImage(GOval newImage) {
		image = newImage;
	}
}
