package game;

import acm.graphics.*;
import starter.GraphicsPane;
import starter.MainApplication;
import utility.CollisionHandler;

public final class CollisionChecker {
	
	/* Checks collision between the portal and player */
	private static boolean playerPortalCollision(GObject player, GOval portal) {
		return CollisionHandler.checkCircRectCollision(player, portal);
	}
	
	/* Checks collision between the player and payload */
	private static boolean playerPayloadCollision(GObject player, GRect payload) {
		if (payload == null) {
			return false;
		}
		return CollisionHandler.checkCircRectCollision(player, payload);
	}
	
	// this function will check collisions between player, portals, and payloads
	public static void collisions(GraphicsPane level, GObject player,GOval portal, GRect payload, boolean payloadGotten ) {
		
		// if the payload has been retrieved and the player and payload have collided, switch screens
		if (payloadGotten && playerPortalCollision(player,portal)) {
			level.switchScreen();
		}
		
		// if the payload and player collide, remove the payload
		if (playerPayloadCollision(player,payload)) {
			level.removePayload();
		}
	}
	
}
