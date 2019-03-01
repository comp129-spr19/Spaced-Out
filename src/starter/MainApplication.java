package starter;

/*********************************************
 * @authors Danilo, Bette, David, Ivan, Steven
 *********************************************/

public class MainApplication extends GraphicsApplication {
	/*************
	 * VARIABLES *
	 *************/
	/* CONSTANTS */
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_EXTENSION = 140;
	public static final String MUSIC_FOLDER = "sounds";

	/* PRIVATE VARIABLES */
	private int currentIndex;// keeps track of the current level we are on.
	private IntroPane introPane;
	private Level[] levels;

	/*****************
	 * SCREEN SET UP *
	 *****************/
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_EXTENSION);
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public void run() {
		introPane = new IntroPane(this);
		currentIndex = 0;
		switchToScreen(introPane);
	}

	/*******************************
	 * WINDOW SWITCHING AND HIDING *
	 *******************************/

	// If movingRight true, we move to the next possible screen
	public void switchLevel(boolean movingRight) {
		if (movingRight) {
			currentIndex++;
			levels[currentIndex].startTimer();
			switchToScreen(levels[currentIndex]);
			// otherwise we move left.
		} else {
			currentIndex--;
			levels[currentIndex].startTimer();
			switchToScreen(levels[currentIndex]);
		}
	}

	// Switch screen to the first level
	public void switchToLevelOne(int factorial) {
		initLevelArray(factorial);
		levels[currentIndex].startTimer();
		switchToScreen(levels[currentIndex]);
	}

	/******************
	 * HELPER METHODS *
	 ******************/
	// Calculates the middle height of the window
	public static int centerHeight(int objectHeight) {
		return (WINDOW_HEIGHT / 2) - (objectHeight / 2);
	}

	// Initializes the array of levels
	public void initLevelArray(int factorial) {
		levels = new Level[factorial];
		Level prev = null;
		Level curr = null;
		Level next = null;
		for (int i = 0; i < factorial; i++) {
			if (i == 0) {
				levels[i] = new Level(this, "first", i + 1, factorial);
			} else if (i == factorial - 1) {
				levels[i] = new Level(this, "last", i + 1, factorial);
			} else {
				levels[i] = new Level(this, "mid", i + 1, factorial);
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
}
