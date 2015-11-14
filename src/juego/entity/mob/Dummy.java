package juego.entity.mob;

import juego.graphics.Screen;
import juego.graphics.Sprite;

public class Dummy extends Mob {

	private int anim = 0;
	private int time = 0;
	private int xa = 0, ya = 0;

	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.actualHealth = this.maxHealth = 100;
		sprite = Sprite.zombie_pig_forward;
	}

	public void update() {
		time++;
		// Makes it move randomly across the map
		if ((time % (random.nextInt(100) + 20)) == 0){
			xa = ya = 0;
			if(random.nextInt(2) == 0)
				ya = random.nextInt(3) - 1;
			else
				xa = random.nextInt(3) - 1;
			if(random.nextInt(4) == 0)
				xa = ya = 0;
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
		
		if(this.actualHealth <= 0)
			remove();
	}

	public void render(Screen screen) {
		screen.renderMob((int)x, (int)(y - 19), sprite);
	}

}
