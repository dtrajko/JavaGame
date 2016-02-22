package com.dtrajko.java.game.graphics;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	protected int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite grass   = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite water   = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite wall    = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite bush    = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock    = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite bullet  = new Sprite(16, 0, 3, SpriteSheet.tiles);
	public static Sprite pickaxe = new Sprite(16, 1, 3, SpriteSheet.tiles);
	public static Sprite sword   = new Sprite(16, 0, 4, SpriteSheet.tiles);
	

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
	
	public static Sprite zombie = new Sprite(32, 2, 0, SpriteSheet.mob_down);
	public static Sprite skeleton = new Sprite(32, 2, 0, SpriteSheet.skeleton_down);

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
		setColor(colour);
	}

	public Sprite(int size, int colour) {
		// System.out.println("Sprite constructor, size: " + size);
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(colour);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		// this.pixels = pixels;
		this.pixels = new int[pixels.length];
		// System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}

	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}

	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		double nx_x = rotateX(-angle, 1.0, 0.0);
		double nx_y = rotateY(-angle, 1.0, 0.0);
		double ny_x = rotateX(-angle, 0.0, 1.0);
		double ny_y = rotateY(-angle, 0.0, 1.0);

		double x0 = rotateX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotateY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		return result;
	}

	private static double rotateX(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * cos + y * -sin;
	}

	private static double rotateY(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * sin + y * cos;
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
	}

	private void setColor(int colour) {
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
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}
}
