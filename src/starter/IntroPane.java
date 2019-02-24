package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.GImage;

public class IntroPane extends GraphicsPane implements ActionListener {
	
	private static final int INTRO_TIMEOUT = 22000;
	
	private MainApplication program;
	private GImage introVideo, introSplashScreen;
	private AudioPlayer introSound;
	private Timer endIntro;

	public IntroPane(MainApplication app) {
		super();
		program = app;
		introVideo = new GImage("GradiusIntro.gif");
		introSplashScreen = new GImage("SpacedOutLogoScreen.gif");
		introVideo.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		introSplashScreen.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		introSound = AudioPlayer.getInstance();
		endIntro = new Timer(INTRO_TIMEOUT, this);
	}	

	@Override
	public void showContents() {
		program.add(introVideo);
		introSound.playSound("", "sounds/IntroScreenAudio.mp3");
	}

	@Override
	public void hideContents() {
		program.remove(introVideo);
		program.remove(introSplashScreen);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!endIntro.isRunning()) {
			program.add(introSplashScreen);
			introSound.stopSound("", "sounds/IntroScreenAudio.mp3");
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int keyEvent = e.getKeyCode();
		switch(keyEvent) {
		case KeyEvent.VK_ENTER: 
			program.switchToLevelOne();
			break;
		}		
	}
}
