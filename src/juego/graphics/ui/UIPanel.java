package juego.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import juego.util.Vector2i;

public class UIPanel extends UIComponent{

	private List<UIComponent> components = new ArrayList<>();
	private Vector2i size;
	
	public UIPanel(Vector2i position, Vector2i size){
		super(position);
		this.size = size;
		color = new Color(0xff404040);
	}
	
	public void addComponent(UIComponent component){
		component.init(this);
		components.add(component);
	}
	
	public void update(){
		for(UIComponent component : components){
			component.setOffset(position);
			component.update(); 
		}
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);
		for(UIComponent component : components){
			component.render(g);
		}	
	}
	
}
