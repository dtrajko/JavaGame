package com.dtrajko.java.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
    public final int SIZE;
    public int SPRITE_WIDTH, SPRITE_HEIGHT;
    private int width, height;
	public int[] pixels;

	public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 256);
	public static SpriteSheet player = new SpriteSheet("/textures/optimus_sprite.png", 128);
	
	public static SpriteSheet player_anim = new SpriteSheet("/textures/optimus_sprite.png", 128, 96);
	public static SpriteSheet player_anim_up    = new SpriteSheet(player_anim, 0, 0, 1, 3, 32);
	public static SpriteSheet player_anim_right = new SpriteSheet(player_anim, 1, 0, 1, 3, 32);
	public static SpriteSheet player_anim_down  = new SpriteSheet(player_anim, 2, 0, 1, 3, 32);
	public static SpriteSheet player_anim_left  = new SpriteSheet(player_anim, 3, 0, 1, 3, 32);

	public static SpriteSheet mob = new SpriteSheet("/textures/zombie_mob_sprite.png", 128, 96);
	public static SpriteSheet mob_up    = new SpriteSheet(mob, 0, 0, 1, 3, 32);
	public static SpriteSheet mob_right = new SpriteSheet(mob, 1, 0, 1, 3, 32);
	public static SpriteSheet mob_down  = new SpriteSheet(mob, 2, 0, 1, 3, 32);
	public static SpriteSheet mob_left  = new SpriteSheet(mob, 3, 0, 1, 3, 32);

	public static SpriteSheet skeleton = new SpriteSheet("/textures/skeleton_mob_sprite.png", 128, 96);
	public static SpriteSheet skeleton_up    = new SpriteSheet(skeleton, 0, 0, 1, 3, 32);
	public static SpriteSheet skeleton_right = new SpriteSheet(skeleton, 1, 0, 1, 3, 32);
	public static SpriteSheet skeleton_down  = new SpriteSheet(skeleton, 2, 0, 1, 3, 32);
	public static SpriteSheet skeleton_left  = new SpriteSheet(skeleton, 3, 0, 1, 3, 32);

	public static SpriteSheet star = new SpriteSheet("/textures/star_mob_sprite.png", 128, 96);
	public static SpriteSheet star_up    = new SpriteSheet(star, 0, 0, 1, 3, 32);
	public static SpriteSheet star_right = new SpriteSheet(star, 1, 0, 1, 3, 32);
	public static SpriteSheet star_down  = new SpriteSheet(star, 2, 0, 1, 3, 32);
	public static SpriteSheet star_left  = new SpriteSheet(star, 3, 0, 1, 3, 32);

	private Sprite[] sprites;

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		sprites = new Sprite[width * height];
		int frame = 0;
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}
	
	public Sprite[] getSprites() {
		// System.out.println("SpriteSheet.getSprites().length: " + sprites.length);
		return sprites;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	private void load() {
		try {
			System.out.print("Trying to load: " + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println(" succeeded!");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			System.out.println("SpriteSheet load - W: " + width + ", H: " + height);
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(" failed!");
		}
	}
}
