package com.dtrajko.java.game.graphics.ui;

import com.dtrajko.java.game.level.Level;

public class UIButtonListener {

	public void entered(UIButton button) {
		button.setColor(0xCCCCCC);
		System.out.println("Mouse entered the '" + button.label.text + "' button!");
	}

	public void exited(UIButton button) {
		button.setColor(0xAAAAAA);
		System.out.println("Mouse exited the '" + button.label.text + "' button!");
	}

	public void pressed(UIButton button) {
		button.setColor(0xEEEEEE);
		System.out.println("Button '" + button.label.text + "' pressed!");
	}

	public void released(UIButton button) {
		button.setColor(0xCCCCCC);
		System.out.println("Button '" + button.label.text + "' released!");
	}
}
