package juego.entity.mob;

import java.util.List;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.util.Vector2i;

public class Chaser extends Mob{

	private int anim = 0;
	private double xa = 0, ya = 0;
	private double speed = 0.85;
	
	public Chaser(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		damage = 1.5;
		position = new Vector2i(x, y);
		this.actualHealth = this.maxHealth = 150;
		sprite = Sprite.zombie_pig_forward;
	}
	
	private void move(){
		xa = ya = 0;
		
		// List of all the players in a radius of 96px or 6 tiles
		List<Player> players = level.getPlayers(this, 96);
		// If there's someone in its range...
		if(players.size() > 0){
			// Gets the first one from the list
			Player player = players.get(0);
			
			// Chasing simple algorithm
			if(x < player.getX()) xa += speed;
			else if(x > player.getX()) xa -= speed;
			
			if(y < player.getY()) ya += speed;
			else if(y > player.getY()) ya -= speed;
			
			// Makes it so if the player's X/Y value positions are slightly off, the mob doesn't go crazy trying to 
			// compensate it. This is because of our movement in this game; since its handled with doubles that stuff
			// can happen quite often if not all of the time.
			if (Math.floor(x) == Math.floor(player.getX())) xa = 0;
			if (Math.floor(y) == Math.floor(player.getY())) ya = 0;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
			if (dir == Direction.UP)
				sprite = Sprite.zombie_pig_backward;
			else if (dir == Direction.DOWN)
				sprite = Sprite.zombie_pig_forward;
			else if (dir == Direction.RIGHT)
				sprite = Sprite.zombie_pig_right;
			else if (dir == Direction.LEFT)
				sprite = Sprite.zombie_pig_left;
		}
		
		if(this.actualHealth <= 0)
			remove();
	}
	
	public void update() {
		checkHit();
		if(this.actualHealth <= 0)
			remove();
		
		move();
		position.set(getTileX(), getTileY());
		
		if (anim < 7500)
			anim++;
		else
			anim = 0;
		
		// Update sprites depending on where he's going
		if (ya < 0) {
			dir = Direction.UP;
			if (anim % 20 > 10)
				sprite = Sprite.zombie_pig_backward1;
			else
				sprite = Sprite.zombie_pig_backward2;
		} else if (ya > 0) {
			dir = Direction.DOWN;
			if (anim % 20 > 10)
				sprite = Sprite.zombie_pig_forward1;
			else
				sprite = Sprite.zombie_pig_forward2;
		}
		if (xa > 0) {
			dir = Direction.RIGHT;
			if (anim % 20 > 10)
				sprite = Sprite.zombie_pig_right1;
			else
				sprite = Sprite.zombie_pig_right2;

		} else if (xa < 0) {
			sprite = Sprite.zombie_pig_left;
			dir = Direction.LEFT;

			if (anim % 20 > 10)
				sprite = Sprite.zombie_pig_left1;
			else
				sprite = Sprite.zombie_pig_left2;
		}
	}

	public void render(Screen screen) {
		screen.renderMob((int)(x),(int)(y - 19), this);
	}

}
