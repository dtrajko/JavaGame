package com.dtrajko.java.game.level;

import com.dtrajko.java.game.Game;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.level.tile.Tile;

public class Level {

	protected int width, height;
	protected Tile[] tiles;
	protected int[] tilesInt;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {
	}

	public void update() {
	}

	private void time() {		
	} 

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				// getTile(x, y).render(x, y, screen);
				if (x >= 0 && x < 64 && y >= 0 && y < 64) {
					tiles[x + y * 64].render(x, y, screen);
				} else {
					Tile.water.render(x, y, screen);
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if (tilesInt[x + y * width] <= 8) {
			return Tile.grass;
		} else if (tilesInt[x + y * width] <= 9) {
			return Tile.wall;
		} else if (tilesInt[x + y * width] == 10) {
			return Tile.water;
		} else {
			return Tile.water;
		}
	}
}
