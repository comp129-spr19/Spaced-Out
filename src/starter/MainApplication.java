package starter;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class MainApplication extends GraphicsApplication {
	/******************
	 * GLOBAL VARIABLES
	 ******************/

	public static final int MAX_LEVELS = 3;

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private Level[] levels;
	private IntroPane introPane;
	private int count;

	// keeps track of the current level we are on.
	private int currentIndex;

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
		//levels = new Level[MAX_LEVELS];
		introPane = new IntroPane(this);
		currentIndex = 0;

		switchToScreen(introPane);
	}

	/*****************************
	 * WINDOW SWITCHING AND HIDING
	 *****************************/

	// if movingRight true, we move to the next possible screen,
	// otherwise we move left.
	public void switchLevel(boolean movingRight) {
		if (movingRight) {
			currentIndex++;
			levels[currentIndex].startTimer();
			switchToScreen(levels[currentIndex]);
		} else {
			currentIndex--;
			levels[currentIndex].startTimer();
			switchToScreen(levels[currentIndex]);
		}
	}
	/*
	 * public void switchToMenu() { count++; levelOne.startTimer();
	 * switchToScreen(levelOne); }
	 * 
	 * public void switchToSome() { levelTwo.startTimer(); switchToScreen(levelTwo);
	 * }
	 */

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

	// initialize the array of levels.
	public void initLevelArray(int factorial) {
		levels = new Level[factorial];
		Level prev = null;
		Level curr = null;
		Level next = null;
		for (int i = 0; i < factorial; i++) {
			if (i == 0) {
				levels[i] = new Level(this, "first", i + 1,factorial);
			} else if (i == factorial - 1) {
				levels[i] = new Level(this, "last", i + 1,factorial);
			} else {
				levels[i] = new Level(this, "mid", i + 1,factorial);
			}
			next = levels[i];

			if (curr != null) {
				curr.setNext(next);
				curr.setPrev(prev);
			}

			prev = curr;
			curr = next;

		}

		curr.setPrev(prev);
		curr.setNext(null);
	}

	// switch screen to the first level
	public void switchToLevelOne(int factorial) {
		initLevelArray(factorial);
		levels[currentIndex].startTimer();
		switchToScreen(levels[currentIndex]);
	}
}
