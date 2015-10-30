package juego.entity.mob;

import juego.entity.Entity;
import juego.entity.projectile.Projectile;
import juego.entity.projectile.WizardProjectile;
import juego.graphics.Sprite;

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
    	// Adds a new WizardProjectile in x and y in the desired direction and adds it into the projectile ArrayList
    	// in Level.java
    	Projectile p = new WizardProjectile(x, y, dir);
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
    private boolean collision(int xa, int ya){
    	boolean walkable = true;
    	for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 12 - 7) >> 4;
    		int yt = ((y + ya) + c / 2 * 12 - 2) >> 4;
    		if(!level.getTile(xt, yt).walkable()) 
        		walkable = false;
		}
        return !walkable;
    }
    
    
}
