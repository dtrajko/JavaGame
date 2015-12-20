package com.dtrajko.java.game.entity;

import java.util.Random;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.level.Level;

public abstract class Entity {

	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();

	public void init(Level level) {
		this.level = level;
		System.out.println("Level: " + level);
	}

	public void update() {		
	}

	public void render(Screen screen) {
		
	}

	public void remove() {
		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
}
