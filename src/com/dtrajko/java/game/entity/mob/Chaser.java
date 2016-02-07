package com.dtrajko.java.game.entity.mob;

import java.util.List;

import com.dtrajko.java.game.entity.mob.Mob.Direction;
import com.dtrajko.java.game.graphics.AnimatedSprite;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.graphics.SpriteSheet;

public class Chaser extends Mob {

	private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.skeleton_down,  32, 32, 3);
	private AnimatedSprite up    = new AnimatedSprite(SpriteSheet.skeleton_up,    32, 32, 3);
	private AnimatedSprite left  = new AnimatedSprite(SpriteSheet.skeleton_left,  32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.skeleton_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int time;
	private double xa = 0;
	private double ya = 0;
	private double speed = 0.8;

	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.skeleton;
		this.life = 6;
	}

	public void move() {
		time++;
		List<Player> players = level.getPlayers(this, 100);
		if (players.size() > 0) {
			Player player = players.get(0);
			// Player player = level.getClientPlayer();
			xa = 0;
			ya = 0;
			if (x < player.getX()) xa += speed;
			if (x > player.getX()) xa -= speed;
			if (y < player.getY()) ya += speed;
			if (y > player.getY()) ya -= speed;
		} else if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}
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
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 0);
	}
}
