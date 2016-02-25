package com.dtrajko.java.game.entity.mob;

import java.awt.Font;
import java.util.List;

import org.w3c.dom.ranges.RangeException;

import com.dtrajko.java.game.Game;
import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.entity.projectile.WizardProjectile;
import com.dtrajko.java.game.graphics.AnimatedSprite;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.graphics.SpriteSheet;
import com.dtrajko.java.game.graphics.ui.UILabel;
import com.dtrajko.java.game.graphics.ui.UIManager;
import com.dtrajko.java.game.graphics.ui.UIPanel;
import com.dtrajko.java.game.graphics.ui.UIProgressBar;
import com.dtrajko.java.game.input.Keyboard;
import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.util.Debug;
import com.dtrajko.java.game.util.Vector2i;

public class Player extends Mob {

	private String name;
	private int lives = 5;
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private Projectile p;
	private int fireRate = 0;
	private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.player_anim_down, 32, 32, 3);
	private AnimatedSprite up    = new AnimatedSprite(SpriteSheet.player_anim_up, 32, 32, 3);
	private AnimatedSprite left  = new AnimatedSprite(SpriteSheet.player_anim_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_anim_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;
	private double speed = 2;
	private int time;
	private UIManager ui;
	private UIProgressBar uiHealthBar;

	@Deprecated
	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		input.right = true;
		sprite = Sprite.player_forward;
		// animSprite = down;
		// update();
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		// make it turn right at the beginning
		input.down = true;
		sprite = Sprite.player_forward;
		fireRate = WizardProjectile.FIRE_RATE;

		ui = Game.getUIManager();
		UIPanel panel = new UIPanel(new Vector2i(300 * Game.scale, 0 * Game.scale), new Vector2i(100 * Game.scale, 225 * Game.scale));
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(10, 28), name).setColor(0xccff00);
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);
		String lifesString = "";
		for (int i = 1; i <= lives; i++) {
			lifesString += "♥";
		}
		UILabel uiLivesLabel = new UILabel(new Vector2i(130, 35), lifesString);
		uiLivesLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
		uiLivesLabel.setColor(0xEE3030);
		panel.addComponent(uiLivesLabel);
		uiHealthBar = new UIProgressBar(new Vector2i(10, 45), new Vector2i(280, 20));
		uiHealthBar.setColor(0x000000);
		uiHealthBar.setFgColor(0xEE3030);
		panel.addComponent(uiHealthBar);

		UILabel healthLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 16)), "Health");
		healthLabel.setColor(0xFFFFFF);
		healthLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel.addComponent(healthLabel);

		// Player default attributes
		health = 100;
		// animSprite = down;
		// update();
	}

	public String getName() {
		return name;
	}

	public void decreaseLives() {
		if (lives < 0) {
			throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "No more lives left!");
		}
		lives--;
	}

	public void update() {
		/**
		List<Entity> es = level.getEntities(this, 80);
		System.out.println("Entities nearby: " + es.size());
		for (Entity e : es) {
			System.out.println(e);
		}
		*/
		if (walking) {
			animSprite.update();			
		} else {
			animSprite.setFrame(0);
		}
		if (fireRate > 0) fireRate--;
		double xa = 0, ya = 0;
		// double speed = 2;
		speed = (this.input.sprint) ? 5 : 2;
		// System.out.println("Current speed: " + speed);
		if (anim < 8600) anim++;
		else anim = 0;
		if (input.up) {
			animSprite = up;
			ya -= speed;
		} else if (input.down) {
			animSprite = down;
			ya += speed;
		}
		if (input.left) {
			animSprite = left;
			xa -= speed;
		} else if (input.right) {
			animSprite = right;
			xa += speed;
		}

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
		// System.out.println("Total projectiles: " + level.getProjectiles().size());
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			// click_count++;
			// if (click_count % 3 == 0) { // shoot on each 4th click
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			// System.out.println("updateShooting dx: " + dx + " dy: " + dy + " dir: " + dir);
			shoot(x, y, dir);
			fireRate = WizardProjectile.FIRE_RATE;
		}
		if (input.space && fireRate <= 0) {
			double shoot_direction = 0;
			if (dir == Direction.RIGHT) {
				shoot_direction = 0;
			} else if (dir == Direction.LEFT) {
				shoot_direction = Math.PI;
			} else if (dir == Direction.UP) {
				shoot_direction = Math.PI * 1.5;
			} else if (dir == Direction.DOWN) {
				shoot_direction = Math.PI * 0.5;
			}
			shoot(x, y, shoot_direction);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	
		uiHealthBar.setProgress((100.0 - time++ % 100) / 100.0);
	}

	public void render(Screen screen) {
		int flip = 0;
		int xx = (int) x;
		int yy = (int) y;
		sprite = animSprite.getSprite();
		screen.renderMob(xx, yy, sprite, flip);
		// Debug.drawRect(screen, 20, 20, 100, 40, 0xff0000, false);
		// Debug.drawRect(screen, 20, 20, 100, 40, 0xff0000, true);
	}
}
