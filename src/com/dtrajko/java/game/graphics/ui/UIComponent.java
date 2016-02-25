package com.dtrajko.java.game.graphics.ui;

import com.dtrajko.java.game.graphics.Screen;
import com.dtrajko.java.game.util.Vector2i;

public class UIComponent {

	public int backgroundColor;
	public Vector2i position, offset;

	public UIComponent(Vector2i position) {
		this.position = position;
		this.offset = new Vector2i();
	}


	public void update() {
		
	}

	public void render(Screen screen) {
		
	}

	public void setOffset(Vector2i offset) {
		this.offset = offset;
	}
}
