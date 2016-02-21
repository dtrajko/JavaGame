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

import javax.swing.JFrame;

import com.dtrajko.java.game.entity.mob.Dummy;
import com.dtrajko.java.game.entity.mob.Mob;
import com.dtrajko.java.game.entity.mob.Player;
import com.dtrajko.java.game.graphics.Font;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.input.Keyboard;
import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.level.Level;
import com.dtrajko.java.game.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 400;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "Game";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	public Level level;
	private Player player;
	// private Mob mob;
	private boolean running = false;

	private Screen screen;
	private Font font;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public int x = 0, y = 0;

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(19, 62);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		// player.init(level);
		level.add(player);
		font = new Font();
		// mob = new Dummy(25, 57);
		// mob.init(level);
		// level = new RandomLevel(64, 64);
        // setFocusable(true);
        // requestFocusInWindow();
		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
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

	public void update() {
		key.update();
		// player.update();
		// mob.update();
		level.update();
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
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);
		font.render(20, 40, -2, 0x880000, "AB(ab)de,fgh\nijklmpqrtuwxy", screen);
		// player.render(screen);
		// mob.render(screen);
		// screen.render(x, y);

		/*
		Sprite sprite = new Sprite(80, 80, 0xff00ff);
		screen.renderSprite(0, 0, sprite, false);
		*/

		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		// work with Graphics
		// g.setColor(Color.BLACK);
		// g.fillRect(0, 0, getWidth(), getHeight());
		// g.fillRect(Mouse.getX() - 16, Mouse.getY() - 16, 32, 32);
		g.fillOval(Mouse.getX() - 16, Mouse.getY() - 16, 32, 32);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		/*
		g.setFont(new Font("Verdana", Font.BOLD, 24));
		g.setColor(Color.GREEN);
		// g.setColor(Color.WHITE);
		g.drawString("X: " + player.getX() + " | Y:" + player.getY() + " | mX: " + Mouse.getX() + " | mY: " + Mouse.getY() +
			" | mB: " + Mouse.getButton(), 20, 660);
		*/
		g.dispose();
		bs.show();
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
