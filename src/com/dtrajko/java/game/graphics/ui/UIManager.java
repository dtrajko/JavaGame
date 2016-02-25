package com.dtrajko.java.game.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List ;

public class UIManager {

	private List<UIPanel> panels = new ArrayList<UIPanel>();

	public UIManager() {
		
	}

	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}

	public void update() {
		for (UIPanel panel : panels) {
			panel.update();
		}
	}

	public void render(Graphics g) {
		// screen.drawRect(320, 0, 80, 224, 0x0000ff, false);
		for (UIPanel panel : panels) {
			panel.render(g);
		}
	}
}
