package com.dtrajko.java.game.entity.mob;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.entity.projectile.WizardProjectile;
import com.dtrajko.java.game.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		} else {
			// pass
		}
	}

	public void update() {
	}

	private boolean collision(int xa, int ya) {
		boolean solid = false;
		int xOffset = 0;
		int yOffset = 0;
		if (xa < 0) xOffset = 8;
		if (xa > 0) xOffset = 8;
		if (ya < 0) yOffset = 0;
		if (ya > 0) yOffset = 20;
		for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 12 + xOffset) / 16;
			int yt = ((y + ya) + c / 2 * 12 + yOffset) / 16;
			if (level.getTile(xt, yt).solid()) {
				solid = true;
			}
		}
		// return level.getTile((int) (x + xa + xOffset) / 16, (int) (y + ya + yOffset) / 16).solid();
		return solid;
		/**
		// int xCorrection = 0;
		// int yCorrection = 0;
		// if (xa < 0) xCorrection = 0;
		// if (xa > 0) xCorrection = 0;
		// if (ya < 0) yCorrection = 0;
		// if (ya > 0) yCorrection = 0;

		// System.out.println(xa + ", " + ya);
		
		*/
	}

	protected void shoot(int x, int y, double dir) {
		// dir *=180 / Math.PI;
		// System.out.println("Angle: " + Math.round(dir));
		Projectile p = new WizardProjectile(x + 8, y + 8, dir);
		level.addProjectile(p);
	}

	public void render() {
	}
}
