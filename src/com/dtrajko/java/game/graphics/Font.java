package com.dtrajko.java.game.graphics;

public class Font {

	private static SpriteSheet font = new SpriteSheet("/fonts/arial.png", 16);
	private static Sprite[] characters = Sprite.split(font);

	public static String charIndex =
		"ABCDEFGHIJKLM" + //
		"NOPQRSTUVWXYZ" + //
		"abcdefghijklm" + //
		"nopqrstuvwxyz" + //
		"0123456789.,'" + //
		"'\"\";:!@$%()-+"; // ‘’“”

	public Font() {
		
	}

	public void render(String text, Screen screen) {
		int x = 50;
		int y = 50;
		int xOffset = 0;
		for (int i = 0; i < text.length(); i++) {
			int yOffset = 0;
			char currentChar = text.charAt(i);
			/*
			if (currentChar == 'g' ||
				currentChar == 'p' ||
				currentChar == 'q' ||
				currentChar == 'y') {
				yOffset = 4;
			}
			if (currentChar == 'j') {
				yOffset = 1;
			}
			*/
			switch (currentChar) {
				case 'g':
				case 'p':
				case 'q':
				case 'y':
					yOffset = 4;
					break;
				case ',':
				case '(':
				case ')':
					yOffset = 2;
					break;
				case 'j':
					yOffset = 1;
					break;
				default:
					yOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			if (index == -1) continue;
			screen.renderSprite(x + i * 16, y + yOffset, characters[index], false);
		}
	}
}
