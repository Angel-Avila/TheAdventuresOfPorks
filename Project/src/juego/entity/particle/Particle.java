package src.juego.entity.particle;

import java.util.ArrayList;
import java.util.List;

import src.juego.entity.Entity;
import src.juego.graphics.Screen;
import src.juego.graphics.Sprite;

public class Particle extends Entity{

	private static List<Particle> particles = new ArrayList<Particle>();
	private Sprite sprite;
	
	private int life;
	
	protected double xx, yy, xa, ya;
	
	public Particle(int x, int y, int life){
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life;
		sprite = Sprite.particle_wizard_p;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
	}
	
	public void update(){
		this.xx += xa;
		this.yy += ya;
	}
	
	public void render(Screen screen){
		screen.renderSprite((int) xx, (int) yy, sprite, true);
	}
}
