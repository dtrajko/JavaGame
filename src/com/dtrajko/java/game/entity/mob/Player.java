package com.dtrajko.java.game.entity.mob;

import com.dtrajko.java.game.Game;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.input.Keyboard;
import com.dtrajko.java.game.input.Mouse;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private int click_count = 0;
	
	public Player(Keyboard input) {
		this.input = input;
		input.right = true;
		// update();
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		// make it turn right at the beginning
		input.down = true;
		// update();
	}

	public void update() {
		int xa = 0, ya = 0;
		if (anim < 400) anim++;
		else anim = 0;
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
		System.out.println("Total projectiles: " + level.getProjectiles().size());
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1) {
			click_count++;
			if (click_count % 3 == 0) { // shoot on each 4th click
				double dx = Mouse.getX() - Game.getWindowWidth() / 2;
				double dy = Mouse.getY() - Game.getWindowHeight() / 2;
				double dir = Math.atan2(dy, dx);
				// System.out.println("updateShooting dx: " + dx + " dy: " + dy + " dir: " + dir);
				shoot(x - 24, y - 20, dir);
				// shoot(x, y, dir);
			}
		}
	}

	public void render(Screen screen) {
		int flip = 0;
		if (dir == 0) {
			sprite = Sprite.player_forward;
			if (walking) {
				if (anim % 60 > 30) {
					sprite = Sprite.player_forward_1;
				} else {
					sprite = Sprite.player_forward_2;
				}
			}
		}
		if (dir == 1 || dir == 3) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 60 > 30) {
					sprite = Sprite.player_side_1;
				} else {
					sprite = Sprite.player_side_2;
				}
			}
			if (dir == 3) {
				flip = 1;
			}
		}
		if (dir == 2) {
			sprite = Sprite.player_back;
			if (walking) {
				if (anim % 60 > 30) {
					sprite = Sprite.player_back_1;
				} else {
					sprite = Sprite.player_back_2;
				}
			}
		}
		int xx = x - Sprite.player.SIZE;
		int yy = y - Sprite.player.SIZE;
		screen.renderPlayer(xx, yy, sprite, flip);
		// if (dir == 1) sprite = Sprite.player_right;
		// if (dir == 3) sprite = Sprite.player_left;
	}
}
