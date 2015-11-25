package juego.entity;

import java.awt.Color;
import java.util.Random;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.Level;
import juego.util.Vector2i;

public class Entity {
 
    protected double x, y;
    protected Sprite sprite;
    private boolean removed = false;
    protected boolean hit = false;
    public Level level;
    protected final Random random = new Random();
    public Vector2i position;
    public double damage;
    
    public Entity(){
    	
    }
    
    public Entity(int x, int y, Sprite sprite){
    	this.x = x;
    	this.y = y ;
    	position = new Vector2i(x, y);
    	damage = 0;
    	this.sprite = sprite;
    }
    
    public void update(){
        
    }
    
    public void render(Screen screen){
        
    }
    
    public double getX(){
    	return x;
    }
    
    public double getY(){
    	return y;
    }
    
    public int getTileX(){
    	return (int)(x + 7) >> 4;
    }
    
    public int getTileY(){
    	return (int)(y + 6) >> 4;
    }
    
    public int[] getXY(){
		int[] r = new int[2];
		r[0] = (int) x;
		r[1] = (int) y;
		return r;
	}
    
    public void remove(){
        // Remove from level
        removed = true;
    }
    
    public void unRemove(){
    	removed = false;
    }
    
    public boolean isRemoved(){
        return removed;
    }
    
    public void hitEntity(double damage){

    }
    
    public boolean isHit(){
    	return hit;
    }
    
    public Sprite getSprite(){
    	return this.sprite;
    }
    
    public void init(Level level){
    	this.level = level;
    }

	public Color getColor() {
		return new Color(0xff0000);
	}

	public void renderMiniMap(Screen screen) {
		screen.renderMobMiniMap(this);
	}
}
