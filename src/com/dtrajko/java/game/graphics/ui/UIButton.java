package com.dtrajko.java.game.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.dtrajko.java.game.input.Mouse;
import com.dtrajko.java.game.util.Vector2i;

public class UIButton extends UIComponent {

	public UILabel label;
	private UIButtonListener buttonListener;
	private UIActionListener actionListener;
	private Color fgColor;

	private Image image;

	private boolean inside = false;
	private boolean pressed = false;
	private boolean ignorePressed = false;
	// private boolean blocked = false;
	private boolean ignoreAction = false;
	private int padding = 5;

	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		Vector2i lp = new Vector2i(position);
		lp.x += 3;
		lp.y += size.y - 3;
		label = new UILabel(lp, "");
		label.setFont(new Font("SansSerif", Font.BOLD, 12));
		label.setColor(0x444444);
		label.active = false;
		init();
	}

	public UIButton(Vector2i position, Vector2i size, BufferedImage image, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		Vector2i lp = new Vector2i(position);
		lp.x += 3;
		lp.y += size.y - 3;
		label = new UILabel(lp, "");
		label.setFont(new Font("SansSerif", Font.BOLD, 12));
		label.setColor(0x444444);
		label.active = false;
		setImage(image);
		init();
	}

	private void init() {
		setColor(0xAAAAAA);
		buttonListener = new UIButtonListener();		
	}

	public void init(UIPanel panel) {
		super.init(panel);
		if (label != null) {
			panel.addComponent(label);			
		}
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public void setButtonListener(UIButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public void setFgColor(int color) {
		this.fgColor = new Color(color);
	}

	public void setText(String text) {
		if (text == "") {
			label.active = false;
		} else {
			label.text = text;
		}
	}

	public void performAction() {
		actionListener.perform();
	}

	public void ignoreNextPress() {
		ignoreAction = true;
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
		boolean leftMouseButtonDown = Mouse.getButton() == MouseEvent.BUTTON1;
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
			if (!inside) {
				if (leftMouseButtonDown) {
					ignorePressed = true;
					System.out.println("Entered while pressed!");
				} else {
					ignorePressed = false;
				}
				buttonListener.entered(this);					
			}
			inside = true;
			if (!pressed && !ignorePressed && leftMouseButtonDown) {
				buttonListener.pressed(this);
				pressed = true;
			} else if (Mouse.getButton() == MouseEvent.NOBUTTON) {
				if (pressed) {
					buttonListener.released(this);
					actionListener.perform();
					pressed = false;					
				}
				ignorePressed = false;
			}
		} else {
			if (inside) {
				buttonListener.exited(this);
				pressed = false;
			}
			inside = false;
		}
	}

	public void render(Graphics g) {
		int x = position.x + offset.x + padding;
		int y = position.y + offset.y + padding;
	
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);

		if (image != null) {
			g.drawImage(image, x, y, null);
		} else {
			if (label != null) {
				label.render(g);
			}
		}
	}
}
