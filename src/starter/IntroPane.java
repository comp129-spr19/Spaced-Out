package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import acm.graphics.GImage;

public class IntroPane extends GraphicsPane implements ActionListener {
	
	private static final int INTRO_DURATION = 23; // duration of intro in seconds
	private static final int SPLASHSCREEN_DURATION = 5; // duration of splashscreen in seconds
	private static final int TIMER_TICK = 1000; // time in milliseconds between timer tick events
	
	private MainApplication program; // graphics program variable
	private GImage introVideo, introSplashScreen; // variables for intro screens
	private AudioPlayer introSound, splashScreenSound; // variables for intro sounds
	private Timer endIntro, endSplashScreen; // timers for intro screens
	private int introTime, splashScreenTime; // time counters for timers

	// Intro screen constructor
	public IntroPane(MainApplication app) {
		super();
		program = app;
		introVideo = new GImage("GradiusIntro.gif");
		introSplashScreen = new GImage("SpacedOutLogoScreen.gif");
		introVideo.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		introSplashScreen.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		introSound = AudioPlayer.getInstance();
		splashScreenSound = AudioPlayer.getInstance();
		endIntro = new Timer(TIMER_TICK, this);
		endSplashScreen = new Timer(TIMER_TICK, this);
		introTime = 0;
		splashScreenTime = 0;
	}	

	@Override
	public void showContents() {
		program.add(introVideo);
		introSound.playSound("sounds", "GradiusIntroAudio.mp3");
		endIntro.start();
	}

	@Override
	public void hideContents() {
		program.remove(introSplashScreen);
		splashScreenSound.stopSound("sounds", "SpeacedOutScreenMusic.mp3");
		endSplashScreen.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == endIntro) {
			
			introTime++;
			
			if (introTime == INTRO_DURATION) {
				switchIntroScreens();
			}
		}
		else {
			
			splashScreenTime++;
			
			if (splashScreenTime == SPLASHSCREEN_DURATION) {
				program.switchToLevelOne();
			}
		}		
	}	
	
	// This is supposed to switch to the splash screen, 
	// but it doesn't currently work
	public void keyPressed(KeyEvent e) {
		int keyEvent = e.getKeyCode();
		switch (keyEvent) {
		case KeyEvent.VK_ENTER:
			switchIntroScreens();
			break;
		}		
	}
	
	// Refactored function to switch from the intro to 
	// the splash screen
	private void switchIntroScreens() {
		program.remove(introVideo);
		introSound.stopSound("sounds", "GradiusIntroAudio.mp3");
		endIntro.stop();
		program.add(introSplashScreen);
		splashScreenSound.playSound("sounds", "SpeacedOutScreenMusic.mp3");
		endSplashScreen.start();
	}
}
