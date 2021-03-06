package com.dtrajko.java.game.level;

import java.util.Random;

public class RandomLevel extends Level {

	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x + y * width] = random.nextInt(12);
				// System.out.println("X: " + x + " Y: " + y + " TILE TYPE: " + tiles[x + y * width]);
			}
		}
	}
}
