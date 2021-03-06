package com.dtrajko.java.game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.mob.Mob;
import com.dtrajko.java.game.entity.mob.Player;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.events.Event;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.layers.Layer;
import com.dtrajko.java.game.level.tile.GrassTile;
import com.dtrajko.java.game.level.tile.Tile;
import com.dtrajko.java.game.level.tile.WallTile;
import com.dtrajko.java.game.level.tile.WaterTile;
import com.dtrajko.java.game.util.Vector2i;

public class Level extends Layer {

	public int width, height;
	// protected Tile[] tiles;
	// protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size = 16;
	public int total_entities = 50;

	private int xScroll, yScroll;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) {
				return +1;
			}
			if (n1.fCost > n0.fCost) {
				return -1;
			}
			return 0;
		}
	};

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

	public boolean isComplete() {
		boolean complete = false;
		// if 
		return complete;
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

	public void onEvent(Event event) {
		getClientPlayer().onEvent(event);
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

	public void removeAll() {
		for (int i = 0; i < entities.size(); i++) {
			entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			// players.remove(i);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Not working well in current implementation
	 * @param start
	 * @param goal
	 * @return
	 */
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				System.out.println("Tile at (" + (x + xi)+ ", " + (y + yi) + ") is " + 
				    (at.solid() ? "SOLID" : "NOT SOLID"));
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vectorInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vectorInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	private boolean vectorInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) {
				return true;
			}
		}
		return false;
	}

	public double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.equals(e)) continue;
			int x = (int) entity.getX();
			int y = (int) entity.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance <= radius) {
				result.add(entity);
			}
		}
		return result;
	}

	public List<Mob> getMobs(Entity e, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		List<Entity> es = getEntities(e, radius);
		for (int i = 0; i < es.size(); i++) {
			if (es.get(i) instanceof Mob) {
				result.add((Mob) es.get(i));
			}
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int) player.getX();
			int y = (int) player.getY();
			
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

	public Mob projectileMobCollision(int x, int y) {
		int precision = 30;
		for (int i = 0; i < entities.size(); i++) {
			if (Math.abs(entities.get(i).getX() - x) < precision && Math.abs(entities.get(i).getY() - y) < precision) {
				if (entities.get(i) instanceof Mob) {
					return (Mob) entities.get(i);					
				}
			}
		}
		for (int i = 0; i < players.size(); i++) {
			if (Math.abs(players.get(i).getX() - x) < precision && Math.abs(players.get(i).getY() - y) < precision) {
				if (players.get(i) instanceof Player) {
					return (Mob) players.get(i);	
				}
			}
		}
		return null;
	}

	public boolean playerMobCollision(int x, int y) {
		boolean result = false;
		int precision = 30;
		for (int i = 0; i < entities.size(); i++) {
			if (Math.abs(entities.get(i).getX() - x) < precision && Math.abs(entities.get(i).getY() - y) < precision) {
				if (entities.get(i) instanceof Mob) {
					System.out.println("playerMobCollision: (" + x + ":" + y + ")");
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	public void render(Screen screen) {
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
