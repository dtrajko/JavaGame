package com.dtrajko.java.game.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.dtrajko.java.game.util.Vector2i;

public class UIPanel extends UIComponent {

	private List<UIComponent> components = new ArrayList<UIComponent>();
	private Vector2i position, size;
	// private Sprite sprite;
	private Color color;

	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		this.position = position;
		this.size = size;
		this.color = new Color(0xaa444444, true);
		// sprite = new Sprite(100, 225, 0xcacaca);
	}

	public void addComponent(UIComponent component) {
		components.add(component);
	}

	public void update() {
		for (UIComponent component : components) {
			component.setOffset(position);
			component.update();
		}
	}

	public void render(Graphics g) {
		// screen.renderSprite(position.x, position.y, sprite, false);
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);
		for (UIComponent component : components) {
			component.render(g);
		}
	}
}
