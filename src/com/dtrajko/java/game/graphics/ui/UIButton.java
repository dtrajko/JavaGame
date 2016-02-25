package com.dtrajko.java.game.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.dtrajko.java.game.util.Vector2i;

public class UIButton extends UIComponent {

	private UIButtonListener buttonListener;
	private Color fgColor;
	private Vector2i size;

	public UIButton(Vector2i position, Vector2i size) {
		super(position);
		this.size = size;
	}

	public void setFgColor(int color) {
		this.fgColor = new Color(color);
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);

		g.setColor(fgColor);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
	}
}
