package starter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import acm.graphics.*;
import utility.CollisionHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**********************
 * @authors Danilo, Bette, David, Ivan, Steven
 **********************/
public class GamePane extends GraphicsPane implements ActionListener{
	/**********************
	 * GLOBAL VARIABLES
	 **********************/
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	public static final int PLAYER_SIZE = 50;
	public static final int PLAYER_START_W = 120;
	public static final int PORTAL_HEIGHT = 150;
	public static final int PORTAL_WIDTH = 20;
	public static final int PORTAL_START_W = 100;
	public static final int PAYLOAD_HEIGHT = 50;
	public static final int PAYLOAD_WIDTH = 60;
	public static final int PAYLOAD_START_W = 600;	
	public static final int VELOCITY = 2;
	public static final int TIMER = 1000;
	
	private GOval portal;
	private GOval player;
	private GRect payload;
	private boolean payloadGotten = false;
	private Timer movement;

	/**********************
	 * CONSTRUCTOR
	 * @param app
	 **********************/	
	public GamePane(MainApplication app) {
		//program set up
		this.program = app;
		//Graphics set up
		player = new GOval(PLAYER_START_W, MainApplication.centerHeight(PLAYER_SIZE), PLAYER_SIZE, PLAYER_SIZE);
		player.setFilled(true);
		portal = new GOval(PORTAL_START_W, MainApplication.centerHeight(PORTAL_HEIGHT), PORTAL_WIDTH, PORTAL_HEIGHT);
		payload = new GRect(PAYLOAD_START_W, MainApplication.centerHeight(PAYLOAD_HEIGHT), PAYLOAD_WIDTH, PAYLOAD_HEIGHT);
		
		//timer initialized and started
		movement = new Timer(TIMER, this);
		movement.start();
		
		//listeners set up
		program.addKeyListeners();
		program.addMouseListeners();	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//THIS NEEDS WORK STEVEN!!!
		
	}
	/**********************
	 * KEY AND MOUSE LISTENER METHODS
	 * @param e
	 **********************/
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
		if (obj == portal && payloadGotten) {
			program.switchToMenu();
		}
		else if (obj == payload) {
			program.remove(payload);
			payloadGotten = true;
		}
	}
	/**********************
	 * WINDOW SWITCHING AND HIDING
	 **********************/
	@Override
	public void hideContents() {
		program.removeAll();
	}
	@Override
	public void showContents() {
		program.add(portal);
		program.add(player);
		if(!payloadGotten) {
			program.add(payload);
		}
	}


}
