package juego.entity.projectile;

import juego.entity.spawner.ParticleSpawner;
import juego.graphics.Screen;
import juego.graphics.Sprite;

public class WizardProjectile extends Projectile{
	
	public static final int FIRE_RATE = 15; // The higher, the slower
	public static final int WIZARD_P_SIZE = 10;
	public WizardProjectile(double x, double y, double dir) {
		super(x - 2, y - 7, dir);
		range = 110;
		speed = 3;
		damage = 20;
		sprite = Sprite.wizard_projectile;
		
		// Vector algebra here; nx and ny are the coordinates where our projectile wants to go to
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update(){
		// If there's a collision where the projectile wants to go, it spawns a particle spawner there
		if(level.tileProjectileCollision((int)(x + nx) + 8, (int)(y + ny) + 8, WIZARD_P_SIZE, 3, 3)){
			// The -1 and the +9 are just some offsets I had to add to make the explosion match where the projectile 
			// actually exploded.
			level.add(new ParticleSpawner((int) x + 6, (int) y + 9, 150, 18, level));
			remove();
		}
		move();
	}
	
	protected void move(){		
		x += nx;
		y += ny;
		
		if(distance() > range)
			remove();
	}
	
	private double distance() {
		double dist = 0;
		// Pythagoras
		dist = Math.sqrt((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y));
		return dist;
		
	}

	// Renders projectile with an Offset to make it match the game
	public void render(Screen screen){
		screen.renderProjectile((int)x,(int) y, this);
	  
	}
}
