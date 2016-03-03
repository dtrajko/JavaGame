package com.dtrajko.java.game.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.dtrajko.java.game.util.Vector2i;

public class UIComponent {

	public String UID = "";
	public int bgColor;
	public Vector2i position;
	public Vector2i size;
	protected Vector2i offset;
	public Color color;
	public boolean active = true;
	public UIPanel panel;

	public UIComponent(Vector2i position) {
		this.position = position;
		this.size = new Vector2i();
		this.offset = new Vector2i();
	}

	public UIComponent(Vector2i position, Vector2i size) {
		this.position = position;
		this.size = size;
		this.offset = new Vector2i();
	}

	public UIComponent setColor(int color) {
		this.color = new Color(color);
		return this;
	}

	public void setOffset(Vector2i offset) {
		this.offset = offset;
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		
	}

	public Vector2i getAbsolutePosition() {
		return new Vector2i(position).add(offset);
	}

	public void init(UIPanel panel) {
		this.panel = panel;
	}
}
