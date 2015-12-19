package com.dtrajko.java.game.level.tile;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile mushroom = new BushTile(Sprite.bush);
	public static Tile rock = new RockTile(Sprite.rock);

	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
		// screen.renderTile(x, y, this);
	}

	public boolean solid() {
		return false;
	}
}
