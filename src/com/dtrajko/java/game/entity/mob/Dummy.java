package com.dtrajko.java.game.entity.mob;

import com.dtrajko.java.game.entity.mob.Mob.Direction;
import com.dtrajko.java.game.graphics.AnimatedSprite;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.graphics.SpriteSheet;

public class Dummy extends Mob {

	private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.mob_down,  32, 32, 3);
	private AnimatedSprite up    = new AnimatedSprite(SpriteSheet.mob_up,    32, 32, 3);
	private AnimatedSprite left  = new AnimatedSprite(SpriteSheet.mob_left,  32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.mob_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;

	/*
	public static Sprite mob = new Sprite(32, 3, 0, SpriteSheet.mob);
	public static Sprite mob_up      = new Sprite(32, 0, 0, SpriteSheet.mob);
	public static Sprite mob_down    = new Sprite(32, 2, 0, SpriteSheet.mob);
	public static Sprite mob_left    = new Sprite(32, 3, 0, SpriteSheet.mob);
	public static Sprite mob_right   = new Sprite(32, 1, 0, SpriteSheet.mob);
	public static Sprite mob_up_1    = new Sprite(32, 0, 1, SpriteSheet.mob);
	public static Sprite mob_down_1  = new Sprite(32, 2, 1, SpriteSheet.mob);
	public static Sprite mob_left_1  = new Sprite(32, 3, 1, SpriteSheet.mob);
	public static Sprite mob_right_1 = new Sprite(32, 1, 1, SpriteSheet.mob);
	public static Sprite mob_up_2    = new Sprite(32, 0, 2, SpriteSheet.mob);
	public static Sprite mob_down_2  = new Sprite(32, 2, 2, SpriteSheet.mob);
	public static Sprite mob_left_2  = new Sprite(32, 3, 2, SpriteSheet.mob);
	public static Sprite mob_right_2 = new Sprite(32, 1, 2, SpriteSheet.mob);
	*/
	// protected AnimatedSprite animSprite = down;

	private int time;
	private int xa = 0;
	private int ya = 0;
	
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}

	public void update() {
		time++;
		if (time % (random.nextInt(20) + 20) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}
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
		int flip = 0;
		sprite = animSprite.getSprite();
		screen.renderMob(x, y, sprite, flip);
	}
}
