package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;

public class IntroPane extends GraphicsPane implements ActionListener {

	private static final int INTRO_DURATION = 17; // duration of intro in seconds
	private static final int SPLASHSCREEN_DURATION = 5; // duration of splashscreen in seconds
	private static final int TIMER_TICK_INTRO = 1050; // time in milliseconds between timer tick events
	private static final int TIMER_TICK_SPLASH = 280; // time in milliseconds between timer tick events
	private static final double WINDOW_WIDTH = MainApplication.WINDOW_WIDTH;
	private static final double WINDOW_HEIGHT = MainApplication.WINDOW_HEIGHT;
	private MainApplication program; // graphics program variable
	private GImage introVideo, introSplashScreen; // variables for intro screens
	private AudioPlayer introSound, splashScreenSound; // variables for intro sounds
	private Timer endIntro, endSplashScreen; // timers for intro screens
	private int introTime, splashScreenTime; // time counters for timers
	private GLabel pressEnter;

	// Intro screen constructor
	public IntroPane(MainApplication app) {
		super();
		program = app;
		introVideo = new GImage("gintro_opt.gif");
		pressEnter = new GLabel("Press 'Enter' to Skip");
		pressEnter.setFont("ocraextended-30");
		pressEnter.setLocation(WINDOW_WIDTH / 2 - pressEnter.getWidth() / 2, WINDOW_HEIGHT * .90);
		pressEnter.setColor(Color.WHITE);
		introSplashScreen = new GImage("spaceout_intro.gif");
		introVideo.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + MainApplication.WINDOW_EXTENSION);
		introSplashScreen.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + MainApplication.WINDOW_EXTENSION);
		introSound = AudioPlayer.getInstance();
		splashScreenSound = AudioPlayer.getInstance();
		endIntro = new Timer(TIMER_TICK_INTRO, this);
		endSplashScreen = new Timer(TIMER_TICK_SPLASH, this);
		introTime = 0;
		splashScreenTime = 0;
	}

	@Override
	public void showContents() {
		program.add(introVideo);
		program.add(pressEnter);
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
		} else {

			splashScreenTime++;

			if (splashScreenTime == SPLASHSCREEN_DURATION) {
				String input;
				int factorial;

				do {
					input = JOptionPane
							.showInputDialog("Please input a number from 2-5 to use as your factorial for this game:");
					try {
						factorial = Integer.parseInt(input);
					} catch (NumberFormatException | NullPointerException ex) {
						factorial = -1;
					}
				} while (factorial <= 1 || factorial > 5);

				program.switchToLevelOne(factorial);
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
		splashScreenSound.playSound("sounds", "SpeacedOutScreenMusic.mp3", true);
		endSplashScreen.start();
	}
}
