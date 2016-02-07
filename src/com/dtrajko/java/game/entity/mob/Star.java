package com.dtrajko.java.game.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import com.dtrajko.java.game.entity.mob.Mob.Direction;
import com.dtrajko.java.game.graphics.AnimatedSprite;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;
import com.dtrajko.java.game.graphics.SpriteSheet;
import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.level.Node;
import com.dtrajko.java.game.level.tile.Tile;
import com.dtrajko.java.game.level.tile.WaterTile;
import com.dtrajko.java.game.util.Vector2i;

public class Star extends Mob {
	private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.star_down,  32, 32, 3);
	private AnimatedSprite up    = new AnimatedSprite(SpriteSheet.star_up,    32, 32, 3);
	private AnimatedSprite left  = new AnimatedSprite(SpriteSheet.star_left,  32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.star_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private double xa = 0;
	private double ya = 0;
	private double speed = 1;
	private List<Node> path = null;
	private int time = 0;

	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.skeleton;
		this.life = 6;
	}

	public void move() {
		xa = 0;
		ya = 0;
		// player coordinates
		int px = (int) level.getPlayerAt(0).getX();
		int py = (int) level.getPlayerAt(0).getY();
		// star mob coordinates
		int sx = (int) getX();
		int sy = (int) getY();
		Vector2i start = new Vector2i(sx >> 4, (int) sy >> 4);
		Vector2i dest = new Vector2i(px >> 4, py >> 4);
		if (time % 10 == 0) {
			path = level.findPath(start, dest);
		}
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				int tx = (int) x >> 4;
				int ty = (int) y >> 4;
				if (tx < vec.getX()) xa++;
				if (tx > vec.getX()) xa--;
				if (ty < vec.getY()) ya++;
				if (ty > vec.getY()) ya--;
				System.out.println("findPath path size: " + path.size() +
						" | Start (" + start.getX() + ", " + start.getY() +
						"), Destination (" + dest.getX() + ", " + dest.getY() + ") ");
				System.out.println("Moving from (" + tx + "," + ty + 
						") to (" + vec.getX() + "," + vec.getY() + ") | XA=" + xa + ", YA: " + ya);
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		time++;
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
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 0);
	}
}
