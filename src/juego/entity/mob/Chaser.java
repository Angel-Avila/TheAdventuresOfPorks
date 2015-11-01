package juego.entity.mob;

import juego.graphics.Screen;
import juego.graphics.Sprite;

public class Chaser extends Mob{

	private int anim = 0;
	private int xa = 0, ya = 0;
	
	public Chaser(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.zombie_pig_forward;
	}
	
	private void move(){
		xa = ya = 0;
		
		Player player = level.getClientPlayer();
		if(x < player.getX() - 16) xa++;
		else if(x > player.getX() + 16) xa--;
		if(y < player.getY()) ya++;
		else if(y > player.getY()) ya--;
		
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
	}
	
	public void update() {
		move();
		if (anim < 7500)
			anim++;
		else
			anim = 0;
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
		screen.renderMob(x - 8, y - 16, this);
	}

}
