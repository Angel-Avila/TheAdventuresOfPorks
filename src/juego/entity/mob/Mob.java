package juego.entity.mob;

import juego.entity.Entity;
import juego.entity.projectile.Projectile;
import juego.entity.projectile.WizardProjectile;
import juego.graphics.Screen;

public abstract class Mob extends Entity{
    
    protected boolean walking = false;
    
    protected enum Direction{
    	UP, DOWN, LEFT, RIGHT
    }
    
    protected Direction dir;
    
    public void move(double xa, double ya){
    	if(xa != 0 && ya != 0){
    		move(xa, 0);
    		move(0, ya);
    		return;
    	}
    	
        if(xa > 0) dir = Direction.RIGHT; 
        if(xa < 0) dir = Direction.LEFT; 
        if(ya > 0) dir = Direction.DOWN; 
        if(ya < 0) dir = Direction.UP;
        
        while(xa != 0){
        	if(Math.abs(xa) > 1){
        		if(!collision(abs(xa), ya))
        			this.x += abs(xa); 
        		xa -= abs(xa);
        	} else{
        		if(!collision(abs(xa), ya))
        			this.x += xa;
        		xa = 0;
        	}
        }
        
        while(ya != 0){
        	if(Math.abs(ya) > 1){
        		if(!collision(xa, abs(ya)))
        			this.y += abs(ya); 
        		ya -= abs(ya);
        	} else{
        		if(!collision(xa, abs(ya)))
        			this.y += ya;
        		ya = 0;
        	}
        }
    }
    
    private int abs(double value){
    	if(value < 0) return -1;
    	return 1;
    }
    
    public abstract void update();
    
    public abstract void render(Screen screen);
    
    protected void shoot(double x, double y, double dir){
    	// Adds a new WizardProjectile in x and y in the desired direction and adds it into the projectile ArrayList
    	// in Level.java
    	Projectile p = new WizardProjectile(x + 3, y + 3, dir);
    	level.add(p);
    }
    /**
     * Checks for collision. xt is the coordinate of the tile in x, this algorithm makes it so it checks every side of
     * the square that is a tile and does the same with yt in y; then it calls level.getTile(xt, yt) to check if the 
     * tile in xt, yt is walkable or not
     * 
     * @param xa
     * @param ya
     * @return
     */
    private boolean collision(double xa, double ya){
    	boolean walkable = true;
    	for (int c = 0; c < 4; c++) {
    		// This algorithm is just to get the 4 corners of the tile and use them to check if they're solid,
    		// the logic is that c % 2 or c / 2 sometimes is 0 and sometimes is 1, so when it's 0 it doesn't do 
    		// anything but when it's 1 it takes 16 (size of the tile) to x, so it checks the other side of it
			double xt = ((x + Math.ceil(xa)) - c % 2 * 16 + 7) / 16;
    		double yt = ((y + Math.ceil(ya)) - c / 2 * 16 + 6) / 16;
    		int ix = (int) Math.ceil(xt);
    		int iy = (int) Math.ceil(yt);
    		if(c % 2 == 0) ix = (int) Math.floor(xt);
    		if(c / 2 == 0) iy = (int) Math.floor(yt);
    		if(!level.getTile(ix, iy).walkable()) 
        		walkable = false;		
		}
    	
        return !walkable;
    }
    
    
}
