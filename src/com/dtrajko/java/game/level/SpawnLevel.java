package com.dtrajko.java.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dtrajko.java.game.level.tile.Tile;

public class SpawnLevel extends Level {

	public int w, h;
	private int[] levelPixels;

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			w = image.getWidth();
			h = image.getHeight();
			tiles = new Tile[w * h];
			levelPixels = new int[w * h];
			image.getRGB(0, 0, w, h, levelPixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file '" + path + "'!");
		}
	}

	// Grass  = 0xFF0000
	// Flower = 0xFFFF00
	// Rock   = 0x7F7F00
	// GrassTile = 00ff00
	// WallTile = ffff00
	// WaterTile = 0000ff
	protected void generateLevel() {
		for (int i = 0; i < levelPixels.length; i++) {
			if (levelPixels[i] == 0xff00ff00) tiles[i] = Tile.grass;
			if (levelPixels[i] == 0xffffff00) tiles[i] = Tile.wall;
			if (levelPixels[i] == 0xff0000ff) tiles[i] = Tile.water;
		}
	}
}
