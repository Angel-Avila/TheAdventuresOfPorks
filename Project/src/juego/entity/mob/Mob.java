package src.juego.entity.mob;

import java.util.ArrayList;
import java.util.List;

import src.juego.entity.Entity;
import src.juego.entity.projectile.Projectile;
import src.juego.entity.projectile.WizardProjectile;
import src.juego.graphics.Sprite;

public abstract class Mob extends Entity{
    
    protected Sprite sprite;
    protected int dir = 0;
    protected boolean moving = false;
    
    public void move(int xa, int ya){
        if(xa > 0) dir = 1; // East
        if(xa < 0) dir = 3; // West
        if(ya > 0) dir = 2; // South
        if(ya < 0) dir = 0; // North
        
        // Sliding
        if(!collision(xa, 0)){
            x += xa; 
        }
        
        if(!collision(0, ya)){
            y += ya;
        }
    }
    
    
    
    public void update(){
        
    }
    
    protected void shoot(int x, int y, double dir){
    	Projectile p = new WizardProjectile(x, y, dir);
    	level.addProjectile(p);
    }
    
    private boolean collision(int xa, int ya){
    	boolean solid = false;
    	for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 12 - 7) >> 4;
    		int yt = ((y + ya) + c / 2 * 12 - 2) >> 4;
    		if(level.getTile(xt, yt).solid()) 
        		solid = true;
		}
        return solid;
    }
    
    
}
