package com.dtrajko.java.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dtrajko.java.game.entity.mob.Chaser;
import com.dtrajko.java.game.entity.mob.Dummy;
import com.dtrajko.java.game.entity.mob.Shooter;
import com.dtrajko.java.game.entity.mob.Star;
import com.dtrajko.java.game.graphics.Sprite;

public class SpawnLevel extends Level {

	// public int w, h;
	// private int[] tiles;

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			// tiles = new Tile[w * h];
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file '" + path + "'!");
		}
		// add(new Dummy(25, 57));
		// add(new Dummy(38, 57));
		// add(new Star(10, 62));
		for (int i = 0; i < 20; i++) {
			int coordX = random.nextInt(64);
			int coordY = random.nextInt(64);
			if (i % 2 == 0) {
				add(new Chaser(coordX, coordY));
			} else {
				add(new Dummy(coordX, coordY));
			}
		}
		add(new Shooter(12, 62));
	}

	// Grass  = 0xFF0000
	// Flower = 0xFFFF00
	// Rock   = 0x7F7F00
	// GrassTile = 00ff00
	// WallTile = ffff00
	// WaterTile = 0000ff
	public void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
		/*
		for (int i = 0; i < levelPixels.length; i++) {
			if (levelPixels[i] == 0xff00ff00) tiles[i] = Tile.grass;
			if (levelPixels[i] == 0xffffff00) tiles[i] = Tile.wall;
			if (levelPixels[i] == 0xff0000ff) tiles[i] = Tile.water;
		}
		*/
	}
}
