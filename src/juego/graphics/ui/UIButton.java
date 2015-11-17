package juego.graphics.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import juego.input.Mouse;
import juego.util.Vector2i;

public class UIButton extends UIComponent{

	public UILabel label;
	private UIButtonListener buttonListener;
	private UIActionListener actionListener;
	
	private boolean inside = false;
	
	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		Vector2i lp = new Vector2i(position);
		lp.x += 4;
		lp.y += size.y - 2;
		label = new UILabel(lp, "");
		label.active = false; 	
		label.setColor(0x444444);
		
		setColor(0xAAAAAA);
		
		buttonListener = new UIButtonListener();
	}
	
	void init(UIPanel panel){
		super.init(panel);
		panel.addComponent(label);
	} 
	public void	setText(String text){
		if(text == "")
			label.active = false;
		else
			label.text = text;
	}
	
	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
		if(rect.contains(new Point(Mouse.getX(), Mouse.getY()))){
			if(!inside)
				buttonListener.entered(this);
			inside = true;
		} else{
			if(inside)
				buttonListener.exited(this);
			inside = false;
		}
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		
		if(label != null)
			label.render(g);
	}
	
}
