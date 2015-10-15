package juego.entity.particle;

import java.util.ArrayList;
import java.util.List;

import juego.entity.Entity;
import juego.graphics.Screen;
import juego.graphics.Sprite;

public class Particle extends Entity{

	private static List<Particle> particles = new ArrayList<Particle>();
	private Sprite sprite;
	
	private int life;
	private int time = 0;
	protected double xx, yy, zz;
	protected double xa, ya, za;
	
	public Particle(int x, int y, int life){
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(30) - 10);
		sprite = Sprite.particle_normal;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 15.0;
	}
	
	public void update(){
		time++;
		if(time > 9000) time = 0;
		if(time > life) remove();
		za -= 0.1;
		
		// Bouncing
		if(zz < 0){
			zz = 0;
			za *= -0.55;
			xa *= 0.4;
			ya *= 0.4;
		}
	
		move(xx + xa, (yy + ya) + (zz + za));
	}
	
	private void move(double x, double y) {
		if(collision(x, y)){
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	private boolean collision(double x, double y){
    	boolean solid = false;
    	for (int c = 0; c < 4; c++) {
    		double xt = (x - c % 2 * 16) / 16;
    		double yt = (y - c / 2 * 16) / 16;
    		int ix = (int) Math.ceil(xt);
    		int iy = (int) Math.ceil(yt);
    		if(c % 2 == 0) ix = (int) Math.floor(xt);
    		if(c / 2 == 0) iy = (int) Math.floor(yt);
    		if(level.getTile(ix, iy).solid()) solid = true;
		}
        return solid;
    }

	public void render(Screen screen){
		// I offset "y" by 14 because without it I feel the particle spawner renders like ~-14 pixels from where the projectile explodes
		screen.renderSprite((int) xx - 1, (int) yy - (int) zz + 14, sprite, true);
	}
}
