package com.dtrajko.java.game.entity.mob;
import java.util.Collections;
import java.util.List;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.graphics.AnimatedSprite;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.graphics.SpriteSheet;
import com.dtrajko.java.game.util.Debug;
import com.dtrajko.java.game.util.Vector2i;

public class Shooter extends Mob {

	private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.skeleton_down,  32, 32, 3);
	private AnimatedSprite up    = new AnimatedSprite(SpriteSheet.skeleton_up,    32, 32, 3);
	private AnimatedSprite left  = new AnimatedSprite(SpriteSheet.skeleton_left,  32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.skeleton_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int time;
	private double xa = 0;
	private double ya = 0;
	private double speed = 0.8;
	private Mob rand = null;

	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.skeleton;
		this.life = 3;
	}

	public void move() {
		time++;
		List<Player> players = level.getPlayers(this, 500);
		if (players.size() > 0) {
			Player player = players.get(0);
			// Player player = level.getClientPlayer();
			xa = 0;
			ya = 0;
			if (x < player.getX()) xa += speed;
			if (x > player.getX()) xa -= speed;
			if (y < player.getY()) ya += speed;
			if (y > player.getY()) ya -= speed;
			// shootClosest();
			shootRandom();
		} else if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}
	}

	private void shootRandom() {
		List<Mob> mobs = level.getMobs(this, 300);
		mobs.add(level.getClientPlayer());
		if (time % (30 + random.nextInt(91)) == 0) {
			int index = random.nextInt(mobs.size());
			rand = mobs.get(index);
		}
		if (rand != null) {
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
		}
	}

	public void shootClosest() {
		List<Mob> mobs = level.getMobs(this, 200);
		mobs.add(level.getClientPlayer());
		double min = 0;
		Entity closest = null;
		for (int i = 0; i < mobs.size(); i++) {
			Entity e = mobs.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) e.getX(), (int) e.getY()));
			if (i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}
		double dx = closest.getX() - x;
		double dy = closest.getY() - y;
		double dir = Math.atan2(dy, dx);
		shoot(x, y, dir);
	}

	public void update() {
		move();
		if (walking) {
			animSprite.update();
		} else {
			animSprite.setFrame(0);
		}
		if (ya < 0) {
			dir = Direction.UP;
			animSprite = up;
		} else  if (ya > 0) {
			dir = Direction.DOWN;
			animSprite = down;
		}
		if (xa < 0) {
			dir = Direction.LEFT;
			animSprite = left;
		} else if (xa > 0) {
			dir = Direction.RIGHT;
			animSprite = right;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, sprite, 0);
	}
}
