package com.dtrajko.java.game.entity.mob;

import java.awt.event.MouseEvent;

import com.dtrajko.java.game.Game;
import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.entity.projectile.WizardProjectile;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.level.tile.Tile;

public abstract class Mob extends Entity {

	public int lives = 5;

	protected boolean moving = false;
	protected boolean walking = false;
	protected int health;
	protected Direction dir;
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

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

	protected void shoot(double x, double y, double dir, Sprite weapon) {
		if (Mouse.getButton() != MouseEvent.NOBUTTON && Mouse.getX() > Game.getWindowWidth()) {
			return;
		}
		Projectile p = new WizardProjectile((int) x + 8, (int) y + 8, dir, this, weapon);
		level.add(p);
		
		// dir *=180 / Math.PI;
		// System.out.println("Angle: " + Math.round(dir));
		// level.addProjectile(p);
	}

	public void render() {
	}

	protected void decreaseLives() {
		this.lives--;
		System.out.println("Mob decrease lives " + lives);
	}

	public void damage() {
		if (this instanceof Player) {
			health -= 1;
			if (health <= 0) {
				decreaseLives();
				health = 100;
			}
		}
	}

	public void wound() {
		if (this instanceof Player) {
			health -= 10;
			if (health <= 0) {
				decreaseLives();
				health = 100;
			}
			System.out.println("The Player is wounded! Health: " + health);
		} else if (this instanceof Mob) {
			this.lives--;
			if (this.lives <= 0) {
				remove();
			}
		}
	}
}
