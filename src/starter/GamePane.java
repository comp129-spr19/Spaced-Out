package starter;
import java.awt.event.MouseEvent;

import acm.graphics.*;

public class GamePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 120;
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_START_W = 100;
	public static final int PAYLOAD_HEIGHT = 50;
	public static final int PAYLOAD_WIDTH = 60;
	public static final int PAYLOAD_START_W = 700;	
	
	private GOval portal;
	private GOval player;
	private GRect payload;
	private boolean payloadGotten = false;

	public GamePane(MainApplication app) {
		this.program = app;
		player = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		player.setFilled(true);
		portal = new GOval(PORTAL_START_W, MainApplication.centerHeight(PORTAL_HEIGHT), PORTAL_WIDTH, PORTAL_HEIGHT);
		payload = new GRect(PAYLOAD_START_W, MainApplication.centerHeight(PAYLOAD_HEIGHT), PAYLOAD_WIDTH, PAYLOAD_HEIGHT);
	}

	@Override
	public void showContents() {
		program.add(portal);
		program.add(player);
		if(!payloadGotten) {
			program.add(payload);
		}
	}

	@Override
	public void hideContents() {
		program.remove(portal);
		program.remove(player);
		program.remove(payload);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == portal && payloadGotten) {
			program.switchToMenu();
		}
		else if (obj == payload) {
			program.remove(payload);
			payloadGotten = true;
		}
	}
}
