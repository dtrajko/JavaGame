package com.dtrajko.java.game.entity.mob;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.w3c.dom.ranges.RangeException;

import com.dtrajko.java.game.Game;
import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.projectile.Projectile;
import com.dtrajko.java.game.entity.projectile.WizardProjectile;
import com.dtrajko.java.game.graphics.AnimatedSprite;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.graphics.SpriteSheet;
import com.dtrajko.java.game.graphics.ui.UIActionListener;
import com.dtrajko.java.game.graphics.ui.UIButton;
import com.dtrajko.java.game.graphics.ui.UIButtonListener;
import com.dtrajko.java.game.graphics.ui.UILabel;
import com.dtrajko.java.game.graphics.ui.UIManager;
import com.dtrajko.java.game.graphics.ui.UIPanel;
import com.dtrajko.java.game.graphics.ui.UIProgressBar;
import com.dtrajko.java.game.input.Keyboard;
import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.util.Debug;
import com.dtrajko.java.game.util.ImageUtils;
import com.dtrajko.java.game.util.Vector2i;

public class Player extends Mob {

 	public List<UIButton> lives_buttons = new ArrayList<UIButton>();
	public Sprite weapon = Sprite.sword;
	public UIProgressBar uiHealthBar;
	public UIProgressBar uiMissionBar;
  
	private String name;
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
	private UIPanel panel;
	private UIButton button_pickaxe;
	private UIButton button_sword;
	private UIButton button_cannonball;
	private UIButton button_img_pickaxe;
	private UIButton button_img_sword;
	private UIButton button_img_cannonball;
	private BufferedImage image, imageHover, imagePressed;

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

		// Player default attributes
		this.lives = 5;
		this.health = 100;

		ui = Game.getUIManager();
		panel = new UIPanel(new Vector2i(300 * Game.scale, 0 * Game.scale), new Vector2i(100 * Game.scale, 225 * Game.scale));
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(10, 230), name).setColor(0xccff00);
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);

		/*
		String livesString = "";
		for (int i = 1; i <= lives; i++) {
			livesString += "♥";
		}
		System.out.println("Player lives: " + livesString);
		UILabel uiLivesLabel = new UILabel(new Vector2i(130, 235), livesString);
		uiLivesLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
		uiLivesLabel.setColor(0xEE3030);
		panel.addComponent(uiLivesLabel);
		*/

		drawLives();

		uiHealthBar = new UIProgressBar(new Vector2i(10, 245), new Vector2i(280, 20));
		uiHealthBar.setColor(0x000000);
		uiHealthBar.setFgColor(0xFF0000);
		panel.addComponent(uiHealthBar);

		UILabel healthLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 16)), "Health");
		healthLabel.setColor(0xFFFFFF);
		healthLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel.addComponent(healthLabel);

		uiMissionBar = new UIProgressBar(new Vector2i(10, 270), new Vector2i(280, 20));
		uiMissionBar.setColor(0x000000);
		uiMissionBar.setFgColor(0x0000FF);
		panel.addComponent(uiMissionBar);

		UILabel missionLabel = new UILabel(new Vector2i(uiMissionBar.position).add(new Vector2i(2, 16)), "Mission Complete");
		missionLabel.setColor(0xFFFFFF);
		missionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel.addComponent(missionLabel);

		// Buttons with images
		try {
			button_img_pickaxe = new UIButton(
				new Vector2i(10, 300),
				new Vector2i(16 * Game.scale + 10, 16 * Game.scale + 10),
				ImageIO.read(new File("res/textures/pickaxe.png")),
				new UIActionListener() {
					public void perform() {
						System.out.println("Weapon 'Pickaxe' selected!");
						level.getClientPlayer().weapon = Sprite.pickaxe;
					}
				}
			);
			button_img_pickaxe.setText("Pickaxe");
			panel.addComponent(button_img_pickaxe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			button_img_sword = new UIButton(
				new Vector2i(70, 300),
				new Vector2i(16 * Game.scale + 10, 16 * Game.scale + 10),
				ImageIO.read(new File("res/textures/sword.png")),
				new UIActionListener() {
					public void perform() {
						System.out.println("Weapon 'Sword' selected!");
						level.getClientPlayer().weapon = Sprite.sword;
					}
				}
			);
			button_img_sword.setText("Sword");
			panel.addComponent(button_img_sword);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			button_img_cannonball = new UIButton(
				new Vector2i(130, 300),
				new Vector2i(16 * Game.scale + 10, 16 * Game.scale + 10),
				ImageIO.read(new File("res/textures/cannonball.png")),
				new UIActionListener() {
					public void perform() {
						System.out.println("Weapon 'Cannonball' selected!");
						level.getClientPlayer().weapon = Sprite.cannonball;
					}
				}
			);
			button_img_cannonball.setText("Cannonball");
			panel.addComponent(button_img_cannonball);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// BEGIN image button with variable brightness
		try {
			image = ImageIO.read(new File("res/textures/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		imageHover = ImageUtils.changeBrightness(image, 80);
		imagePressed = ImageUtils.changeBrightness(image, -60);

		UIButton imageButton = new UIButton(new Vector2i(10, 360), new Vector2i(37, 37), image, new UIActionListener() {
			public void perform() {
				// System.exit(0);
			}
		});
		imageButton.setButtonListener(new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(imageHover);
			}
			public void exited(UIButton button) {
				button.setImage(image);
			}
			public void pressed(UIButton button) {
				button.setImage(imagePressed);
			}
			public void released(UIButton button) {
				button.setImage(image);
			}
		});
		// panel.addComponent(imageButton);
		// END image button with variable brightness

		/*
		button_pickaxe = new UIButton(
			new Vector2i(10, 340), 
			new Vector2i(16 * Game.scale + 10, 16 * Game.scale + 10), 
			new UIActionListener() {
				public void perform() {
					System.out.println("Weapon 'Pickaxe' selected!");
					level.getClientPlayer().weapon = Sprite.pickaxe;
				}
			}
		);
		button_pickaxe.setButtonListener(new UIButtonListener() {
			public void pressed(UIButton button) {
				super.pressed(button);
				// button.performAction();
				button.ignoreNextPress();
			}
		});
		button_pickaxe.setText("Pickaxe");
		panel.addComponent(button_pickaxe);

		button_sword = new UIButton(
			new Vector2i(70, 340),
			new Vector2i(16 * Game.scale + 10, 16 * Game.scale + 10),
			new UIActionListener() {
				public void perform() {
					System.out.println("Weapon 'Sword' selected!");
					level.getClientPlayer().weapon = Sprite.sword;
				}
			}
		);
		button_sword.setText("Sword");
		panel.addComponent(button_sword);

		button_cannonball = new UIButton(
			new Vector2i(130, 340),
			new Vector2i(16 * Game.scale + 10, 16 * Game.scale + 10),
			new UIActionListener() {
				public void perform() {
					System.out.println("Weapon 'Cannonball' selected!");
					level.getClientPlayer().weapon = Sprite.cannonball;
				}
			}
		);
		button_cannonball.setText("Cannonball");
		panel.addComponent(button_cannonball);
		*/

		// animSprite = down;
		// update();
	}

	public String getName() {
		return name;
	}

	protected void decreaseLives() {
		super.decreaseLives();
		panel.removeComponent("life_" + (lives + 1));
		if (lives < 0) {
			lives = 5;
			drawLives();
		}
		System.out.println("Player decrease lives " + lives);
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
		time++;
		if (time % 10 == 0) {
			if (level.playerMobCollision((int) x, (int) y)) {
				damage();
			}			
		}
		updateShooting();
		updateUI();
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
			shoot(x, y, dir, this.weapon);
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
			shoot(x, y, shoot_direction, this.weapon);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}

	private void updateUI() {
		double progress = health / 100.0;
		// System.out.println("Player health: " + health + ", player health progress: " + progress);
		uiHealthBar.setProgress(progress);
		uiMissionBar.setProgress(missionComplete());
		/*
		if (health <= 0) {
			decreaseLives();
		} else {
			// pass
		}
		*/
	}

	public double missionComplete() {
		double completed = 0.0;
		double enemies_total = level.total_entities;
		double enemies_remaining = level.getMobs(this, 10000).size();
		completed = (enemies_total - enemies_remaining) / enemies_total;
		if (time % 40 == 0 &&completed < 1.0) {			
			System.out.println("Remaining " + (int) enemies_remaining + " of total " + (int) enemies_total + ". Completed " + (int) (completed * 100) + "%");
		}
		return completed;
	}

	private void drawLives() {
		System.out.println("drawLives!");
		int lifeCoordX = 160;
		for (int i = 1; i <= lives; i++) {
			try {
				UIButton life_button = new UIButton(
					new Vector2i(lifeCoordX, 210),
					new Vector2i(27, 27),
					ImageIO.read(new File("res/textures/heart.png")),
					new UIActionListener() {
						public void perform() {							
						}
					}
				);
				life_button.setButtonListener(new UIButtonListener() {
					public void entered(UIButton button) {
					}
					public void exited(UIButton button) {
					}
					public void pressed(UIButton button) {
					}
					public void released(UIButton button) {
					}
				});
				life_button.setColor(0x444444);
				life_button.setPadding(0);
				life_button.UID = "life_" + i;
				System.out.println("UID: " + life_button.UID);
				panel.addComponent(life_button);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lifeCoordX += 24;
		}
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
