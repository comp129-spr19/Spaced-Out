package starter;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class MainApplication extends GraphicsApplication {
	/******************
	 * GLOBAL VARIABLES
	 ******************/
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };
	private GamePane gamePane;
	private MenuPane menu;
	private int count;

	/***************
	 * SCREEN SET UP
	 ***************/
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	/*************
	 * CONSTRUCTOR
	 *************/
	public void run() {
		gamePane = new GamePane(this);
		menu = new MenuPane(this);
		switchToMenu();
	}

	/*****************************
	 * WINDOW SWITCHING AND HIDING
	 *****************************/
	public void switchToMenu() {
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		switchToScreen(gamePane);
	}

	/****************
	 * HELPER METHODS
	 ****************/
	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}

	public static int centerHeight(int objectHeight) {
		return (WINDOW_HEIGHT / 2) - (objectHeight / 2);
	}
}
