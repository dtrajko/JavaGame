package com.dtrajko.java.game.graphics.ui;

import com.dtrajko.java.game.graphics.Font;
import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;

	public UILabel(Vector2i position, String text) {
		super(position);
		font = new Font();
		this.text = text;
	}

	public void render(Screen screen) {
		font.render(position.x + offset.x, position.y + offset.y, -4, 0, text, screen);
	}
}
