package com.dtrajko.java.game;

import java.awt.Canvas;
import java.awt.Color;
// import java.awt.Color;
import java.awt.Dimension;
// import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.dtrajko.java.game.entity.mob.Dummy;
import com.dtrajko.java.game.entity.mob.Mob;
import com.dtrajko.java.game.entity.mob.Player;
import com.dtrajko.java.game.events.Event;
import com.dtrajko.java.game.events.EventListener;
import com.dtrajko.java.game.graphics.Font;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.layers.Layer;
import com.dtrajko.java.game.graphics.ui.UIManager;
import com.dtrajko.java.game.graphics.ui.UIPanel;
import com.dtrajko.java.game.input.Keyboard;
import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.level.Level;
import com.dtrajko.java.game.level.TileCoordinate;
import com.dtrajko.java.game.util.Vector2i;

public class Game extends Canvas implements Runnable, EventListener {
	private static final long serialVersionUID = 1L;

	public static int width = 400 - 100;
	public static int height = width / 4 * 3; // 16 * 9;
	public static int scale = 3;
	public static String title = "Game";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	public Level level;
	private Player player;
	// private Mob mob;
	private boolean running = false;

	private static UIManager uiManager; // = new UIManager();

	private Screen screen;
	private Font font;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private List<Layer> layerStack = new ArrayList<Layer>();

	public int x = 0, y = 0;

	public boolean game_over = false;

	public Game() {
		Dimension size = new Dimension(width * scale + 100 * 3, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		uiManager = new UIManager();
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		addLayer(level);
		TileCoordinate playerSpawn = new TileCoordinate(20, 62);
		player = new Player("dtrajko", playerSpawn.x(), playerSpawn.y(), key);
		// player.init(level);
		level.add(player);
		font = new Font();
		addKeyListener(key);
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		// mob = new Dummy(25, 57);
		// mob.init(level);
		// level = new RandomLevel(64, 64);
        // setFocusable(true);
        // requestFocusInWindow();
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static UIManager getUIManager() {
		return uiManager;
	}

	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000 / 40.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			// System.out.println("Running...");
			while (delta >= 1) {
				update(); // a.k.a. tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}

	public void update() {
		if (game_over) {
			return;
		}

		if (player.missionComplete() == 1.0) {
			player.uiMissionBar.setProgress(1.0);
			return;
		}

		key.update();
		// player.update();
		// mob.update();
		uiManager.update();

		// level.update();
		// Update layers here!
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).update();
		}

		// System.out.println("Player lives: " + player.lives);			
		if (player.lives == 0) {
			System.out.println("Game Over!");
			level.removeAll();
			player.uiHealthBar.setProgress(0.0);
			player.uiMissionBar.setProgress(0.0);
			game_over = true;
		}

		/**
		if (key.up) y--;
		if (key.down) y++;
		if (key.left) x--;
		if (key.right) x++;
		*/
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int xScroll = (int) (player.getX() - screen.width / 2 + 16);
		int yScroll = (int) (player.getY() - screen.height / 2);
		level.setScroll(xScroll, yScroll);

		// level.render(screen);
		// Render layers here!
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).render(screen);
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		uiManager.render(g);
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		g.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 20));
		g.setColor(Color.GREEN);
		// g.fillOval(Mouse.getX() - 16, Mouse.getY() - 16, 32, 32);
		g.drawString("X: " + player.getX() + " | Y:" + player.getY() + " | mX: " + Mouse.getX() + " | mY: " + Mouse.getY() +
				" | mB: " + Mouse.getButton(), 20, 660);

		if (game_over) {
			displayGameOver(g);
		}

		if (player.missionComplete() == 1.0) {
			displayMissionComplete(g);
		}

		g.dispose();
		bs.show();

		/**
		font.render(20, 40, -2, 0x880000, "AB(ab)de,fgh\nijklmpqrtuwxy", screen);
		player.render(screen);
		mob.render(screen);
		screen.render(x, y);
		Sprite sprite = new Sprite(80, 80, 0xff00ff);
		screen.renderSprite(0, 0, sprite, false);
		// work with Graphics
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.fillRect(Mouse.getX() - 16, Mouse.getY() - 16, 32, 32);
		g.setFont(new Font("Verdana", Font.BOLD, 24));
		g.setColor(Color.GREEN);
		g.setColor(Color.WHITE);
		*/
	}

	public void displayGameOver(Graphics g) {
		g.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 80));
		g.setColor(Color.BLACK);
		g.drawString("GAME OVER", 175, 365);
		g.setColor(Color.YELLOW);
		g.drawString("GAME OVER", 170, 360);
	}

	public void displayMissionComplete(Graphics g) {
		g.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 74));
		g.setColor(Color.BLACK);
		g.drawString("MISSION COMPLETE!", 15, 365);
		g.setColor(Color.YELLOW);
		g.drawString("MISSION COMPLETE!", 20, 360);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
