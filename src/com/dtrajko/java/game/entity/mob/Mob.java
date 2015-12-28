package com.dtrajko.java.game.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.entity.projectile.WizardProjectile;
import com.dtrajko.java.game.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;

	public void move(int xa, int ya) {
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

		if (!collision(xa, 0)) {
			// x = -1, 0, 1
			x += xa;			
		}

		if (!collision(0, ya)) {
			// y = -1, 0, 1
			y += ya;		
		}
	}

	public void update() {
	}

	protected void shoot(int x, int y, double dir) {
		// dir *=180 / Math.PI;
		// System.out.println("Angle: " + Math.round(dir));
		Projectile p = new WizardProjectile(x, y, dir);
		level.addProjectile(p);
	}

	private boolean collision(int xa, int ya) {
		boolean solid = false;
		int xCorrection = 0;
		int yCorrection = 0;
		if (xa < 0) xCorrection = -32;
		if (xa > 0) xCorrection = 0;
		if (ya < 0) yCorrection = -16;
		if (ya > 0) yCorrection = 0;
		if (level.getTile((x + xCorrection + xa) / 16, (y + yCorrection + ya) / 16).solid()) {
			solid = true;
		}
		System.out.println(xa + ", " + ya);
		return solid;
	}

	public void render() {
	}
}
