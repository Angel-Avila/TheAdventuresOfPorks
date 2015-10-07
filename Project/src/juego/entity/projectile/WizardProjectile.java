package src.juego.entity.projectile;

import src.juego.entity.particle.Particle;
import src.juego.graphics.Screen;
import src.juego.graphics.Sprite;

public class WizardProjectile extends Projectile{
	
	public static final int FIRE_RATE = 15; // The higher, the slower
	public static final int WIZARD_P_SIZE = 10;
	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = 110;
		speed = 3;
		damage = 20;
		sprite = Sprite.wizard_projectile;
		
		// Vector algebra here
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update(){
		if(level.tileCollision(x, y, nx, ny, WIZARD_P_SIZE)){
			Particle p = new Particle((int)x,(int) y, 50);
        	level.add(p);
			remove();
		}
		move();
	}
	
	protected void move(){
		if(!level.tileCollision(x, y, nx, ny, WIZARD_P_SIZE)){
			x += nx;
			y += ny;
		}
		
		if(distance() > range)
			remove();
		
	}
	
	private double distance() {
		double dist = 0;
		// Pythagoras
		dist = Math.sqrt((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y));
		return dist;
		
	}

	public void render(Screen screen){
		screen.renderProjectile((int)x - 8,(int) y - 5, this);
	  
	}
}
