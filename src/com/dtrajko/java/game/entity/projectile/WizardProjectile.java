package com.dtrajko.java.game.entity.projectile;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;

public class WizardProjectile extends Projectile {

	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 20;
		rateOfFire = 15;
		sprite = Sprite.bullet;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
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
