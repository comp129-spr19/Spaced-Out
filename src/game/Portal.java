package game;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GOval;
import starter.MainApplication;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class Portal {
	/***********
	 * VARIABLES
	 ***********/
	// DIMENSIONS AND COORDINATES FOR THE PORTAL
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_LEFT_START_W = 100;
	public static final int PORTAL_RIGHT_START_W = 700;
	
	/* PRIVATE VARIABLES */
	private GImage image;
	
	/*************
	 * CONSTRUCTOR
	 *************/
	public Portal(String portalType) {
		
		/* PORTAL CREATION LOGIC:
		 *  -If specified will create a right portal
		 *  -Else will just create a left portal 
		 */
		if (portalType.equals("right")) {
			image = new GImage("Portal.gif");
			image.setSize(PORTAL_WIDTH, PORTAL_HEIGHT);
			image.setLocation(PORTAL_RIGHT_START_W, MainApplication.WINDOW_HEIGHT / 2 - image.getHeight() / 2 );
		} 
		else if (portalType.equals("left")) {
			image = new GImage("Portal.gif");
			image.setSize(PORTAL_WIDTH, PORTAL_HEIGHT);
			image.setLocation(PORTAL_LEFT_START_W, MainApplication.WINDOW_HEIGHT / 2 - image.getHeight() / 2 );
		}
		
	}
	
	/***********************
	 * SETTERS AND GETTERS *
	 ***********************/
	// Sets a new Portal image
	public void setImage(String newImage) {
		image.setImage(newImage);
		image.setSize(PORTAL_WIDTH, PORTAL_HEIGHT);
	}
	
	// Gets Portal image
	public GImage getImage() {
		return image;
	}
	
}
