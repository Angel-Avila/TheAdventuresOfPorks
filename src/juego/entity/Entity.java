package juego.entity;

import java.util.Random;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.Level;

public class Entity {
 
    protected double x, y;
    protected Sprite sprite;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();
    
    public Entity(){
    	
    }
    
    public Entity(int x, int y, Sprite sprite){
    	this.x = x;
    	this.y = y ;
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
    
    public void remove(){
        // Remove from level
        removed = true;
    }
    
    public boolean isRemoved(){
        return removed;
    }
    
    public Sprite getSprite(){
    	return this.sprite;
    }
    
    public void init(Level level){
    	this.level = level;
    }
}
