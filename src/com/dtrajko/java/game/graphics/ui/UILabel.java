package com.dtrajko.java.game.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.dtrajko.java.game.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	private Color color;

	public UILabel(Vector2i position, String text) {
		super(position);
		font = new Font("SansSerif", Font.PLAIN, 24);
		this.text = text;
		color = new Color(0x333399);
	}

	public UILabel setColor(int color) {
		this.color = new Color(color);
		return this;
	}

	public UILabel setFont(Font font) {
		this.font = font;
		return this;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
		// font.render(position.x + offset.x, position.y + offset.y, -4, 0, text, screen);
	}
}
