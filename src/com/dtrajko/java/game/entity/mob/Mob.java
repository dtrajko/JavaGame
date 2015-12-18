package com.dtrajko.java.game.entity.mob;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;

	public void move(int xa, int ya) {
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

		if (!collision()) {
			// x = -1, 0, 1
			x += xa;
			y += ya;			
		}
	}

	public void update() {
	}

	private boolean collision() {
		return false;
	}

	public void render() {
	}
}
