package com.dtrajko.java.game.entity.projectile;

import com.dtrajko.java.game.entity.spawner.ParticleSpawner;
import com.dtrajko.java.game.entity.spawner.Spawner;
import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.mob.Mob;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.level.tile.Tile;

public class WizardProjectile extends Projectile {
	
	public static final int FIRE_RATE = 10; // Higher is slower!
	private Mob shooter;

	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = 500;
		speed = 4;
		damage = 20;
		sprite = Sprite.bullet;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public WizardProjectile(int x, int y, double dir, Mob shooter) {
		this(x, y, dir);
		this.shooter = shooter;
	}

	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 4, 4)) {
			level.add(new ParticleSpawner((int)x, (int)y, 64, 50, level));
			level.setTile(Tile.grass, (int) (x + nx), (int) (y + ny), 8, 4, 4);
			remove();
		}
		Mob mob;
		if ((mob = level.mobColided((int) x, (int) y)) instanceof Mob) {
			if (mob != this.shooter) {
				level.add(new ParticleSpawner((int)x, (int)y, 64, 50, level));
				mob.hurt();
				remove();
			}
		}
		move();
	}

	protected void move() {
			x += nx;
			y += ny;
		if (distance() > range) remove();
	}

	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.pow(xOrigin - x, 2) + Math.pow(yOrigin - y, 2));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile(x, y, this);
	}
}
