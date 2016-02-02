package com.dtrajko.java.game.entity.mob;

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

	private int xa = 0;
	private int ya = 0;

	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.skeleton;
	}

	public void move() {
		xa = 0;
		ya = 0;
		Player player = level.getClientPlayer();
		if (x < player.getX()) xa++;
		if (x > player.getX()) xa--;
		if (y < player.getY()) ya++;
		if (y > player.getY()) ya--;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
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
	}
	
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x, y, sprite, 0);
	}
}
