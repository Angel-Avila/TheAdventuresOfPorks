package juego.entity.projectile;

import juego.entity.spawner.ParticleSpawner;
import juego.graphics.Screen;
import juego.graphics.Sprite;

public class MobProjectile extends Projectile{
	
	public static final int FIRE_RATE = 15; // The higher, the slower
	public static final int WIZARD_P_SIZE = 10;
	
	int rotation = 0;
	int rotation_angle = 0;
	
	public MobProjectile(double x, double y, double dir) {
		super(x - 2, y - 7, dir);
		range = 110;
		speed = 3;
		damage = 1.5;
		sprite = Sprite.rotate(Sprite.mob_projectile, angle);
		
		// Vector algebra here; nx and ny are the coordinates where our projectile wants to go to
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update(){
		// If there's a collision where the projectile wants to go, it spawns a particle spawner there
		if(level.tileProjectileCollision((int)(x + nx) + 8, (int)(y + ny) + 8, WIZARD_P_SIZE, 3, 3)||
				   level.playerProjectileCollision((int)(x + nx) + 8, (int)(y + ny) + 8, 3, 3, this)){
			// The -1 and the +9 are just some offsets I had to add to make the explosion match where the projectile 
			// actually exploded.
			new ParticleSpawner((int) x + 6, (int) y + 9, 120, 5, level, new Sprite(3, 0xff0000));
			new ParticleSpawner((int) x + 6, (int) y + 9, 120, 2, level, new Sprite(3, 0));
			new ParticleSpawner((int) x + 6, (int) y + 9, 120, 7, level, new Sprite(3, 0xffffff));
			remove();
		}
		
		rotation++;
		rotation_angle++;
		if(rotation > 9000) rotation = 0;
		if(rotation_angle > 360) rotation_angle = 0;
		if(rotation % 5 == 0) sprite = Sprite.rotate(Sprite.mob_projectile, angle - rotation_angle);
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

	// Renders projectile
	public void render(Screen screen){
		screen.renderProjectile((int)x,(int) y, this);
	  
	}
}
