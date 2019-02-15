package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_START_W = 700;
	private GOval portal;
	private GOval player;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		player = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		player.setFilled(true);
		portal = new GOval(PORTAL_START_W, MainApplication.centerHeight(PORTAL_HEIGHT), PORTAL_WIDTH, PORTAL_HEIGHT);
	}

	@Override
	public void showContents() {
		program.add(portal);
		program.add(player);
	}

	@Override
	public void hideContents() {
		program.remove(portal);
		program.remove(player);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == portal) {
			program.switchToSome();
		}
	}
}
