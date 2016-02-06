package com.dtrajko.java.game.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.mob.Mob;
import com.dtrajko.java.game.entity.mob.Player;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.level.tile.GrassTile;
import com.dtrajko.java.game.level.tile.Tile;
import com.dtrajko.java.game.level.tile.WallTile;
import com.dtrajko.java.game.level.tile.WaterTile;

public class Level {

	public int width, height;
	// protected Tile[] tiles;
	// protected int[] tilesInt;
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();

	public static Level spawn = new SpawnLevel("/levels/level_01.png");

	protected Random random = new Random();

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

	public void generateLevel() {
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
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		remove();
	}

	public void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) {
				entities.remove(i);
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) {
				projectiles.remove(i);
			}
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) {
				particles.remove(i);
			}
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) {
				players.remove(i);
			}
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = entity.getX();
			int y = entity.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance <= radius) {
				result.add(entity);
			}
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = player.getX();
			int y = player.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance <= radius) {
				result.add(player);
			}
		}
		return result;
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public Mob mobColided(int x, int y) {
		int precision = 15;
		for (int i = 0; i < entities.size(); i++) {
			if (Math.abs(entities.get(i).getX() - x) < precision && Math.abs(entities.get(i).getY() - y) < precision) {
				if (entities.get(i) instanceof Mob) {
					return (Mob) entities.get(i);					
				}
			}
		}
		return null;
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
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			entities.add(e);			
		}
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	public void addProjectile(Projectile p) {
		p.init(this);
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

	public void setTile(Tile tile, int x, int y, int size, int xOffset, int yOffset) {
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
		    if (xt >= 0 && xt < width && yt >= 0 && yt < height) {
				if (tile instanceof GrassTile) {
					tiles[xt + yt * width] = 0xff00ff00;
				} else if (tile instanceof WallTile) {
					tiles[xt + yt * width] = 0xffffff00;
				} else if (tile instanceof WaterTile) {
					tiles[xt + yt * width] = 0xff0000ff;
				}
		    } 
		}
	}
}
