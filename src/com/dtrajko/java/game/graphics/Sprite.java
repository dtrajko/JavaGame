package com.dtrajko.java.game.graphics;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite bush = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 0, 1, SpriteSheet.tiles);

	public static Sprite voidSprite = new Sprite(16, 0x0066ff);

	/*
	public static Sprite player0 = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite player1 = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite player2 = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite player3 = new Sprite(16, 1, 2, SpriteSheet.tiles);
	public static Sprite player = new Sprite(32, 3, 0, SpriteSheet.tiles);
	public static Sprite player_left = new Sprite(32, 6, 0, SpriteSheet.tiles);
	public static Sprite player_right = new Sprite(32, 4, 0, SpriteSheet.tiles);
	*/

	public static Sprite player = new Sprite(32, 3, 0, SpriteSheet.tiles);

	public static Sprite player_forward   = new Sprite(32, 3, 0, SpriteSheet.tiles);
	public static Sprite player_back      = new Sprite(32, 5, 0, SpriteSheet.tiles);
	public static Sprite player_side      = new Sprite(32, 4, 0, SpriteSheet.tiles);
	public static Sprite player_forward_1 = new Sprite(32, 3, 1, SpriteSheet.tiles);
	public static Sprite player_back_1    = new Sprite(32, 5, 1, SpriteSheet.tiles);
	public static Sprite player_side_1    = new Sprite(32, 4, 1, SpriteSheet.tiles);
	public static Sprite player_forward_2 = new Sprite(32, 3, 2, SpriteSheet.tiles);
	public static Sprite player_back_2    = new Sprite(32, 5, 2, SpriteSheet.tiles);
	public static Sprite player_side_2    = new Sprite(32, 4, 2, SpriteSheet.tiles);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int colour) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}

	private void setColour(int colour) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = colour;
		} 
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}

