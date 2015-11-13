package juego.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import juego.util.Vector2i;

public class UIComponent {
	
	public Vector2i position, offset;
	public int backgroundColor;
	public Color color;
	
	public UIComponent(Vector2i position){
		this.position = position;
		offset = new Vector2i();
	}
	
	public UIComponent setColor(int color){
		this.color = new Color(color);
		return this;
	}
	
	protected void setOffset(Vector2i offset){
		this.offset = offset;
	}
	
	public void update(){
		
	}
	
	public void render(Graphics g){
		
	}
}
