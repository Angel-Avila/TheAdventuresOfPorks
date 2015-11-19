package juego.graphics.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import juego.input.Mouse;
import juego.util.Vector2i;

public class UIButton extends UIComponent {

	public UILabel label;
	private UIButtonListener buttonListener;
	private UIActionListener actionListener;

	private Image image;

	private boolean inside = false;
	private boolean pressed = false;
	private boolean ignorePressed = false;
	private boolean ignoreAction = false;

	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		Vector2i lp = new Vector2i(position);
		lp.x += 4;
		lp.y += size.y - 2;
		label = new UILabel(lp, "");
		label.active = false;
		label.setColor(0x444444);
		init();
	}

	public UIButton(Vector2i position, BufferedImage image, UIActionListener actionListener) {
		super(position, new Vector2i(image.getWidth(), image.getHeight()));
		this.actionListener = actionListener;
		setImage(image);
		init();
	}

	private void init() {
		setColor(0xAAAAAA);
		buttonListener = new UIButtonListener();
	}

	void init(UIPanel panel) {
		super.init(panel);
		if (label != null)
			panel.addComponent(label);
	}

	public void setText(String text) {
		if (text == "")
			label.active = false;
		else
			label.text = text;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public void performAction(){
		actionListener.perform();
	}
	
	public void ignoreNextPress(){
		ignoreAction = true;
	}
	
	public void setButtonListener(UIButtonListener buttonListener){
		this.buttonListener = buttonListener;
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);

		// If the mouse is in the rectangle of our button
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {

			// If the mouse wasn't inside...
			if (!inside) {
				// If someone is already pressing the button and then hovers
				// over it
				if (Mouse.getB() == 1)
					ignorePressed = true;
				else
					ignorePressed = false;

				// we call the listener that says it entered
				buttonListener.entered(this);
			}
			// we set inside to true since it is inside it
			inside = true;

			// If he hadn't pressed the button, and he presses it only until he
			// is inside (ignorePressed)
			// we call the pressed listener and set pressed to true
			if (!pressed && !ignorePressed && Mouse.getB() == 1) {
				pressed = true;
				buttonListener.pressed(this);
				// If he released while being inside the button...
			} else if (Mouse.getB() == MouseEvent.MOUSE_RELEASED) {
				// We check if he had pressed it inside the rect and then...
				if (pressed) {
					// we call the released listener
					buttonListener.released(this);
					// we perform the action
					if (!ignoreAction)
						actionListener.perform();
					else
						ignoreAction = false;
					// and we set pressed to false
					pressed = false;
				}
				// and finally we reset the ignorePressed to false
				ignorePressed = false;
			}

		} else {
			// If inside was true, then he exited the button so we call the
			// listener
			if (inside)
				buttonListener.exited(this);
			// And then reset pressed and inside to false
			pressed = false;
			inside = false;
		}
	}

	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;

		if (image != null) {
			g.drawImage(image, x, y, null);
		} else {
			g.setColor(color);
			g.fillRect(x, y, size.x, size.y);

			if (label != null)
				label.render(g);
		}
	}

}
