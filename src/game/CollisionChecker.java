package game;

import acm.graphics.*;
import starter.AudioPlayer;
import starter.GraphicsPane;
import starter.Level;
import starter.MainApplication;
import utility.CollisionHandler;

public final class CollisionChecker {
	
	/* Checks collision between the portal and player */
	private static boolean playerPortalCollision(GObject player, GImage portal) {
		return CollisionHandler.checkCircRectCollision(player, portal);
	}
	
	/* Checks collision between the player and payload */
	private static boolean playerPayloadCollision(GObject player, GObject payload) {
		if (payload == null) {
			return false;
		}
		return CollisionHandler.checkCircRectCollision(player, payload);
	}
	
	// this function will check collisions between player, portals, and payloads
	public static void collisions(Level level, Player player,Portal portalLeft,Portal portalRight, Payload payload, boolean payloadGotten ) {
		Level next = level.getNext();
		
		
		// if the payload has been retrieved and the player and payload have collided, switch screens
		if (portalLeft != null ) {
			if (payloadGotten && playerPortalCollision(player.getImage(),portalLeft.getImage())) {
				level.switchScreen(false);
			}
		}
		
		// check if a right portal exists
		if (portalRight != null) {
			// if the player hasn't retrieved the payload from the level over, and a level 
			// exists to the right. Check for the right portal collision
			if (next != null && !(next.payloadRetrieved()) &&  playerPortalCollision(player.getImage(),portalRight.getImage())) {
				level.switchScreen(true);
			}
		}
		
		// check if the player has retrieved the next level's payload or if the player is at the end level
		if (next == null ||next.payloadRetrieved()) {
			// if the payload and player collide, remove the payload
			if (playerPayloadCollision(player.getImage(),payload.getImage())) {
				level.removePayload();
			}
		}
		
		// give error message and play error sound when trying to retrieve wrong payload
		if (next != null && !(next.payloadRetrieved())) {
			if (playerPayloadCollision(player.getImage(),payload.getImage())) {
				
			//AudioPlayer.getInstance().playSound("sounds", "error.wav");
			}
		}
	}
	
}
