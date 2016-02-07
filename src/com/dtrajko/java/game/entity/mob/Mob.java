package com.dtrajko.java.game.entity.mob;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.entity.projectile.WizardProjectile;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.level.tile.Tile;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	protected int life = 5;

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	protected Direction dir;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}
	}

	protected int abs(double value) {
		if (value < 0) {
			return -1;
		} else {
			return 1;
		}
	}

	public void update() {
	}

	/**
     * Simple collision method for Mob class, based on checking 8 outer corners of the
     * sprite square (top-left, top-middle, top-right, right-middle, right-down etc)
	 *
	 * @param double xa
	 * @param double ya
	 * @return boolean
	 */
	protected boolean collision(double xa, double ya) {
		boolean solid = false;
		int tsize = Tile.SIZE;
		for (int cx : new int[]{-tsize, 0, tsize}) {
			for (int cy : new int[]{-tsize, 0, tsize}) {
				int ix = (int) Math.ceil((x + xa + cx) / tsize);
				int iy = (int) Math.ceil((y + ya + cy) / tsize);
				if (level.getTile(ix, iy).solid()) solid = true;
			}
		}
		return solid;
	}

	protected void shoot(double x, double y, double dir) {
		// dir *=180 / Math.PI;
		// System.out.println("Angle: " + Math.round(dir));
		Projectile p = new WizardProjectile((int) x + 8, (int) y + 8, dir);
		level.addProjectile(p);
	}

	public void render() {
	}

	public void hurt() {
		this.life--;
		if (this.life <= 0) {
			remove();
		}
	}
}
