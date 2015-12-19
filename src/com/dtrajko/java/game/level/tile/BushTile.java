package com.dtrajko.java.game.level.tile;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;

public class BushTile extends Tile {

	public BushTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	public void render(int x, int y, Screen screen) {
		// Do render stuff here
		screen.renderTile(x << 4, y << 4, this);
	}
}
