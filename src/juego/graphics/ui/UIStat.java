package juego.graphics.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import juego.util.Vector2i;

public class UIStat extends UIComponent {

	public UILabel label;

	private BufferedImage image;

	public UIStat(Vector2i position, BufferedImage image) {
		super(position, new Vector2i(image.getWidth(), image.getHeight()));
		Vector2i lp = new Vector2i(position);
		lp.x += 20;
		lp.y += size.y - 2;
		label = new UILabel(lp, "");
		label.active = false;
		label.setColor(0xffffff);
		label.setFont(new Font("Helvetica", Font.BOLD, 14));
		this.image = image;
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

	public void update() {
		
	}

	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;

		if (image != null) 
			g.drawImage(image, x, y, null);	
		
		if (label != null){
			label.render(g);
		}
	}

}
