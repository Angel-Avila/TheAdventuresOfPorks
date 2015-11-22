package juego.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyListener;

import juego.input.Keyboard;
import juego.input.Mouse;
import juego.util.Vector2i;

public class UITextBox extends UIComponent {

	private UITextBoxListener textBoxListener;
	private UIActionListener actionListener;

	private KeyListener key;
	
	protected boolean active = false;
	
	private String name;

	public UITextBox(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		init();
	}

	private void init() {
		setColor(0xffffff);
		textBoxListener = new UITextBoxListener();
	}

	void init(UIPanel panel) {
		super.init(panel);
	}

	public void performAction() {
		actionListener.perform();
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);

		// If the mouse is in the rectangle of our textBox
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
			// And he clicks it
			if (Mouse.getB() == 1) {
				actionListener.perform();
				textBoxListener.pressed(this);
				active = true;
			} 
		}
		// but he clicks outside the textbox, it's going to change active to false
		else if(Mouse.getB() == 1)
			active = false;
				
		if(active){
			
		}
	}

	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;

		g.setColor(Color.BLACK);
		g.fillRect(x, y, size.x, size.y);
		if(!active){
			setColor(0xffffff);
		}
		else{
			setColor(0xFFDD00);
		}
		g.setColor(color);
		g.drawRect(x, y, size.x, size.y);
	}

}
