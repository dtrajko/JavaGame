package com.dtrajko.java.game.entity.particle;

import java.util.ArrayList;
import java.util.List;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;

public class Particle extends Entity {

	// private static List<Particle> particles = new ArrayList<Particle>();
	private Sprite sprite;

	private int life;
	private int time = 0;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Particle(int x, int y, int life) {
		System.out.println("Particle life: " + life);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		sprite = Sprite.particle_normal;
		// particles.add(this);

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}

	public void update() {
		time++;
		if (time >= 8600) time = 0;
		if (time > life) remove();
		za -= 0.1;

		if (zz < 0) {
			zz = 0;
			za *= -0.55;
			xa *= 0.6;
			ya *= 0.4;
		}

		move(xx + xa, (yy + ya) + (zz + za));
	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			this.xx *= -0.5;
			this.yy *= -0.5;
			this.zz *= -0.5; 
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za; 
	}

	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);
	}
}
