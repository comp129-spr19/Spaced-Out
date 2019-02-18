package starter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acm.graphics.*;

public class MenuPane extends GraphicsPane implements ActionListener {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 20;
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_START_W = 700;
	public static final int VELOCITY = 2;
	
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
	public void keyPressed(KeyEvent e) {
		//Key Press Left or 'A'
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.move(-1 * VELOCITY, 0);
		}
		//Key Press Right or 'D'
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.move(VELOCITY, 0);
		}
		//Key Press Up or 'W'
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			player.move(0, -1 *VELOCITY);
		}
		//Key Press Down or 'S'
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.move(0, VELOCITY);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == portal) {
			program.switchToSome();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
