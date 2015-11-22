package juego.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import juego.input.Keyboard;
import juego.input.Mouse;
import juego.util.Vector2i;

public class UITextBox extends UIComponent {

	private UITextBoxListener textBoxListener;

	Keyboard key;
	
	public static boolean active = false;

	public static String name = "";
	private Font bitMadness;

	public UITextBox(Vector2i position, Vector2i size, Keyboard key) {
		super(position, size);
		bitMadness = new Font("8-Bit Madness", Font.PLAIN, 52);
		this.key = key;
		init();
	}

	private void init() {
		setColor(0xffffff);
		textBoxListener = new UITextBoxListener();
	}

	void init(UIPanel panel) {
		super.init(panel);
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);

		// If the mouse is in the rectangle of our textBox
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
			// And he clicks it
			if (Mouse.getB() == 1) {
				textBoxListener.pressed(this);
				active = true;
			}
		}
		// but he clicks outside the textbox, it's going to change active to
		// false
		else if (Mouse.getB() == 1)
			active = false;
	}

	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;

		g.setColor(Color.BLACK);
		g.fillRect(x, y, size.x, size.y);
		if (!active) {
			setColor(0xffffff);
		} else {
			setColor(0xFFDD00);
		}
		g.setColor(color);
		g.drawRect(x, y, size.x, size.y);
		g.setColor(Color.WHITE);
		g.setFont(bitMadness);
		g.drawString(name, x, y + 30);
	}
}
