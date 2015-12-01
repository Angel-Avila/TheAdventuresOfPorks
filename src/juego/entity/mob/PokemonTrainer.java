package juego.entity.mob;

import java.util.List;

import juego.entity.projectile.WizardProjectile;
import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.util.Vector2i;

public class PokemonTrainer extends Mob{

	private int anim = 0;
	private double xa = 0, ya = 0;
	private double speed = 1;
	private int fireRate = WizardProjectile.FIRE_RATE;
	
	public PokemonTrainer(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		damage = .5;
		position = new Vector2i(x, y);
		this.actualHealth = this.maxHealth = 105;
		sprite = Sprite.pokemon_trainer_forward;
	}
	
	private void move(){
		xa = ya = 0;
		
		// List of all the players in a radius of 112px or 7 tiles
		List<Player> players = level.getPlayers(this, 112);
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
			
			// ----- Some shooting ------
			// We do this since we already have the player list here, just for some optimization
			updateShooting(player);
		}
		
		// Update sprites depending on the direction in which the mob is heading
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
			if (dir == Direction.UP)
				sprite = Sprite.pokemon_trainer_backward;
			else if (dir == Direction.DOWN)
				sprite = Sprite.pokemon_trainer_forward;
			else if (dir == Direction.RIGHT)
				sprite = Sprite.pokemon_trainer_right;
			else if (dir == Direction.LEFT)
				sprite = Sprite.pokemon_trainer_left;
		}
		
		if(this.actualHealth <= 0)
			remove();
	}
	
	public void update() {
		checkHit();
		
		if(this.actualHealth <= 0)
			die();
		
		move();
		position.set(getTileX(), getTileY());
		
		if(fireRate > 0) fireRate--;
		
		if (anim < 7500)
			anim++;
		else
			anim = 0;
		
		// Update sprites depending on the direction in which the mob is heading
		if (ya < 0) {
			dir = Direction.UP;
			if (anim % 20 > 10)
				sprite = Sprite.pokemon_trainer_backward1;
			else
				sprite = Sprite.pokemon_trainer_backward2;
		} else if (ya > 0) {
			dir = Direction.DOWN;
			if (anim % 20 > 10)
				sprite = Sprite.pokemon_trainer_forward1;
			else
				sprite = Sprite.pokemon_trainer_forward2;
		}
		if (xa > 0) {
			dir = Direction.RIGHT;
			if (anim % 30 > 20)
				sprite = Sprite.pokemon_trainer_right1;
			else if(anim % 30 > 10)
				sprite = Sprite.pokemon_trainer_right2;
			else 
				sprite = Sprite.pokemon_trainer_right;

		} else if (xa < 0) {
			sprite = Sprite.pokemon_trainer_left;
			dir = Direction.LEFT;

			if (anim % 30 > 20)
				sprite = Sprite.pokemon_trainer_left1;
			else if(anim % 30 > 10)
				sprite = Sprite.pokemon_trainer_left2;
			else
				sprite = Sprite.pokemon_trainer_left;
		}
	}

	private void updateShooting(Player player) {

		// If the fireRate is <= 0
		if (fireRate <= 0) {
			// Gets the x and y coordinates from where the player is relative to the mob
			double dx = player.getX() - getX();
			double dy = player.getY() - getY();
			// Uses the atan function of y / x to get the angle.
			double dir = Math.atan2(dy, dx);
			// Shoots to the desired direction
			shootPlayer(x, y, dir);
			// Resets the fireRate so it can shoot again
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderMob((int)(x),(int)(y - 19), this);
	}

}
