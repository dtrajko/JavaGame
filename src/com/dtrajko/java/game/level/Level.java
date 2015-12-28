package com.dtrajko.java.game.level;

import java.util.ArrayList;
import java.util.List;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.level.tile.Tile;

public class Level {

	public int width, height;
	// protected Tile[] tiles;
	// protected int[] tilesInt;
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();

	public static Level spawn = new SpawnLevel("/levels/level_01.png");

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		// tiles = new Tile[width * height];
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
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
	}

	public void add(Entity e) {
		entities.add(e);
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.water;
		if (tiles[x + y * width] == 0xff00ff00) {
			return Tile.grass;
		} else if (tiles[x + y * width] == 0xffffff00) {
			return Tile.wall;
		} else if (tiles[x + y * width] == 0xff0000ff) {
			return Tile.water;
		} else {
			return Tile.water;
		}
	}
}
