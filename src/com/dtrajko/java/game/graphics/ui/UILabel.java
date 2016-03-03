package com.dtrajko.java.game.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;

import com.dtrajko.java.game.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	private Color color;
	public boolean dropShadow = false;
	public int dropShadowOffset = 2;

	public UILabel(Vector2i position, String text) {
		super(position, new Vector2i());
		font = new Font("SansSerif", Font.BOLD, 24);
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
		if (dropShadow) {
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
		}
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
		
		/*
		Graphics2D g2d = (Graphics2D)g;
		TextLayout tl = new TextLayout(text, font, g2d.getFontRenderContext());
		Shape shape = tl.getOutline(null);
		g2d.translate(position.x + offset.x, position.y + offset.y);
		g2d.setColor(new Color(0));
		g2d.draw(shape);
		font.render(position.x + offset.x, position.y + offset.y, -4, 0, text, screen);
		*/
	}
}
