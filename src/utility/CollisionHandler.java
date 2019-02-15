package utility;

import acm.graphics.GObject;
import game.Location;

/*
 * Utility class that will perform calculations for collisions. 
 * 
 */

public final class CollisionHandler {

	
	/* Returns the center coordinates of a GObject*/
	public static Location getCenter(GObject object) {
		
		
		double x1 = object.getX();
		double y1 = object.getY();
		
		double x2 = object.getX() + object.getWidth();
		double y2 = object.getY() + object.getHeight();
		
		double centerX = (x2 + x1)/2;
		double centerY = (y2 + y1)/2;
		
		return new Location(centerX,centerY);
				
		
		
	}
	
	/* Checks if collisions between a Rectangle and Circle has occured*/
	public static boolean checkRectCircCollison(GObject rect, GObject cir) {
		Location circCenter = getCenter(cir);
		double circRadius = cir.getHeight()/2;
		
		double closestX = clamp(circCenter.getX(),rect.getX(),rect.getX()+rect.getWidth());
		double closestY = clamp(circCenter.getY(),rect.getY(),rect.getY()+rect.getHeight());
		
		double distX = circCenter.getX() - closestX;
		double distY = circCenter.getY() - closestY;
		
		double distSquared = (distX*distX ) + (distY*distY);
		
		return distSquared < (circRadius * circRadius);
		
	}
	
	
	/* Checks if collisions between two circles has occured */
	public static boolean checkCircCircCollision(GObject cir1, GObject cir2) {
		Location centerCircOne = getCenter(cir1);
		Location centerCircTwo = getCenter(cir2);
		
		double radiusOne = cir1.getHeight()/2;
		double radiusTwo = cir2.getHeight()/2;
		
		double left_side = Math.pow((centerCircTwo.getX()-centerCircOne.getX()), 2) + Math.pow(centerCircTwo.getY()-centerCircOne.getY(), 2);
		
		return (left_side) < (Math.pow(radiusTwo+radiusOne, 2));
	}
	
	/* Checks collisions between two rectangles */

	public static boolean checkRectRectCollision(GObject rect1, GObject rect2) {
		
		// get the top left and bottom right points of each rect	
		Location rectOneTopLeft = new Location(rect1.getX(),rect1.getY());
		Location rectOneBottomRight = new Location(rect1.getX()+rect1.getWidth(),rect1.getY() + rect1.getHeight());
		
		Location rectTwoTopLeft = new Location(rect2.getX(),rect2.getY());
		Location rectTwoBottomRight = new Location(rect2.getX() +rect2.getWidth(), rect2.getY() + rect2.getHeight());
		
		
		// check to see if there is no overlap between these points
		if (rectOneTopLeft.getX() < rectTwoBottomRight.getX() &&
				rectOneBottomRight.getX() > rectTwoTopLeft.getX() &&
				 rectOneTopLeft.getY() < rectTwoBottomRight.getY() &&
				 rectOneBottomRight.getY() > rectTwoTopLeft.getY()) {
			return true;
		} else {
			return false;
		}
			
	}
	
	/* helper method for checkCollisionEdge */
	private static double clamp(double x,double min, double max) {
		if (x < min) {
			x = min;
		} else if (x > max) {
			x = max;
		}
		return x;
	}
}
