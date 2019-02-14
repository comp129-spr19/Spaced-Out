package game;
/*
 * 
 * Location: Saves X and Y coordinates together for encapsulation
 * 
 */

import java.util.*;


public class Location {
	private double x_coord;
	private double y_coord;
	
	/* constructor to create a location object with the parameters provided in the constructor. */
	public Location(double x, double y) {
		x_coord = x;
		y_coord = y;
	}
	
	/* This method will change the x and y coordinates for a Location */
	public void setLocation(double x, double y) {
		x_coord = x;
		y_coord = y;
	}
	
	/* This method will just change the x coordinate */
	public void setX(double x) {
		x_coord = x;
	}
	
	/* This method will change the y_coordinate */
	public void setY(double y) {
		y_coord = y;
	}
	
	/* returns the x coordinate */
	public double getX() {
		return x_coord;
	}
	
	/* returns the y coordinate */
	public double getY() {
		return y_coord;
	}
	
	public String toString() {
		return "x coordinate: " + x_coord + "\n" + "y coordinate: " + y_coord;
	}
	
	
}