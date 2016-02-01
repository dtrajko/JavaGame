package com.dtrajko.java.game.graphics;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	protected int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite grass  = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite water  = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite wall   = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite bush   = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock   = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite bullet = new Sprite(16, 0, 3, SpriteSheet.tiles);

	//Spawn Level Sprites:
	/*
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
	 */

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
	public static Sprite player_forward   = new Sprite(32, 0, 0, SpriteSheet.player);
	public static Sprite player_back      = new Sprite(32, 2, 0, SpriteSheet.player);
	public static Sprite player_side      = new Sprite(32, 1, 0, SpriteSheet.player);
	public static Sprite player_forward_1 = new Sprite(32, 0, 1, SpriteSheet.player);
	public static Sprite player_back_1    = new Sprite(32, 2, 1, SpriteSheet.player);
	public static Sprite player_side_1    = new Sprite(32, 1, 1, SpriteSheet.player);
	public static Sprite player_forward_2 = new Sprite(32, 0, 2, SpriteSheet.player);
	public static Sprite player_back_2    = new Sprite(32, 2, 2, SpriteSheet.player);
	public static Sprite player_side_2    = new Sprite(32, 1, 2, SpriteSheet.player);
	
	public static Sprite dummy = new Sprite(32, 2, 0, SpriteSheet.mob_down);

	public static Sprite particle_normal  = new Sprite(3, 0xffd100);

	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		// pixels = new int[SIZE * SIZE];
		this.sheet = sheet;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColour(colour);
	}

	public Sprite(int size, int colour) {
		// System.out.println("Sprite constructor, size: " + size);
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		} 
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
}

