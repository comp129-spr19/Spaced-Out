package game;

import acm.graphics.*;
import java.awt.Color;

public class Robot extends GCompound {
	
	private static final String IMAGE_FILENAME = "robot.gif";

	private GImage pic;
	private GRect rect;
	
	public Robot(int x, int y, int height, int width, Color c) {
		super();
		setLocation(x, y);
		pic = new GImage(IMAGE_FILENAME, 0, 0);
		pic.setSize(width, height);
		add(pic);
		rect = new GRect(0, 0);
		rect.move((5 * width) / 16, height / 3);
		rect.setSize((5 * width) / 12, (3 * height) / 5);
		rect.setColor(c);
		rect.setFilled(true);
		add(rect);
	}
	
	@Override
	public double getWidth() {
		return pic.getWidth();
	}

	@Override
	public double getHeight() {
		return pic.getHeight();
	}
	
}
