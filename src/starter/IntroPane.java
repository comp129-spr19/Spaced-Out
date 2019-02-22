package starter;

import java.awt.event.KeyEvent;

import acm.graphics.GImage;

public class IntroPane extends GraphicsPane {
	private MainApplication program;
	private GImage introVideo;
	private AudioPlayer introSound;
	
	public IntroPane(MainApplication app) {
		super();
		program = app;
		introVideo = new GImage("gradius Intro.gif");
		introVideo.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		introSound = AudioPlayer.getInstance();
	}
	 
	/* public void keyPressed(KeyEvent e) {
		int keyEvent = e.getKeyCode();
		switch(keyEvent) {
		case KeyEvent.VK_ENTER: 
			program.switchToScreen(IntroSplashScreen);
			break;
		}		
	} */
	
	@Override
	public void showContents() {
		program.add(introVideo);
		introSound.playSound("", "sounds/IntroScreenAudio.mp3");
	}
	
	@Override
	public void hideContents() {
		program.remove(introVideo);
		introSound.stopSound("", "sounds/IntroScreenAudio.mp3");
	}
	
	
}
