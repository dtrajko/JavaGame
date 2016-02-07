package com.dtrajko.java.game.entity;

import java.util.Random;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.level.Level;

/**
 * Entity - abstract class for objects that should be added to a level
 */
public abstract class Entity {

	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();

	public Entity() {		
	}

	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void init(Level level) {
		this.level = level;
		// System.out.println("Level: " + level);
	}

	public void update() {		
	}

	public void render(Screen screen) {
		if (sprite != null) {
			screen.renderSprite((int) x, (int) y, sprite, true);
		}
	}
	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void remove() {
		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
