package juego.entity.particle;

import juego.entity.Entity;
import juego.graphics.Screen;
import juego.graphics.Sprite;

public class Particle extends Entity{

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
		sprite = Sprite.particle_wizard_p;
		
		// xa and ya with randoms are for the movement in this axis; zz is for the bounce. The +(int) in zz
		// can be changed depending on how much you want the particles to bounce after a projectile collision.
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 8.0;
	}
	
	public void update(){
		// Removes the particles if they their lifespan is over in the level
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
	
		// xx is our x but in a double value so we sum xa to simulate some physics, the same goes with yy but we also
		// sum zz and za to simulate bouncing and gravity
		move(xx + xa, yy + ya + zz + za);
	}
	
	// Moves the particles
	private void move(double x, double y) {
		// If there's a collision, they change direction
		if(collision(x, y - zz - za)){
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	
	/**
	 * @param x coordinate
	 * @param y coordinate
	 * xt is the x coordinate depending on the corner the for loop is checking
	 * yt is the y coordinate depending on the corner the for loop is checking
	 * @return if it's solid or not
	 */
	private boolean collision(double x, double y){
    	boolean solid = false;
    	for (int c = 0; c < 4; c++) {
    		// This algorithm is just to get the 4 corners of the tile and use them to check if they're solid,
    		// the logic is that c % 2 or c / 2 sometimes is 0 and sometimes is 1, so when it's 0 it doesn't do 
    		// anything but when it's 1 it takes 16 (size of the tile) to x, so it checks the other side of it
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
		screen.renderSprite((int) xx, (int) yy - (int) zz , sprite, true);
	}
}
