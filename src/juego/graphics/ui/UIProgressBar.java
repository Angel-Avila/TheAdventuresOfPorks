package juego.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import juego.util.Vector2i;

public class UIProgressBar extends UIComponent{

	private Vector2i size;
	private double progress; // 0.0 - 1.0
	private Color foregroundColor;
	
	public UIProgressBar(Vector2i position, Vector2i size) {
		super(position);
		this.size = size;
		this.foregroundColor = new Color(0xffffff);
	}
	
	public void setForegroundColor(int color){
		this.foregroundColor = new Color(color);
	}
	
	// Maximum progress is always 1
	public void setProgress(double progress){
		this.progress = (progress > 1.0) ? 1.0 : progress;
		this.progress = (progress < 0.0) ? 0.0 : progress;
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		// This is the normal rectangle of our progress bar
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		
		// This renders the actual points we have left, it's the rectangle with the different color
		g.setColor(foregroundColor);
		g.fillRect(position.x + offset.x, position.y + offset.y, (int)(progress * size.x), size.y);
	}
	
	

}
