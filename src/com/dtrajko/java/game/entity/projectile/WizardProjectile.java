package com.dtrajko.java.game.entity.projectile;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;

public class WizardProjectile extends Projectile {
	
	public static final int FIRE_RATE = 10; // Higher is slower!

	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 20;
		sprite = Sprite.bullet;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		move();
	}

	protected void move() {
		if (!level.tileCollision(x, y, nx, ny, 8)) {
			x += nx;
			y += ny;
		}
		if (distance() > range) remove();
		// System.out.println("Distance: " + calculateDistance());
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
