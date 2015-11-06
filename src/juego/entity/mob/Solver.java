package juego.entity.mob;

import java.util.List;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.Node;
import juego.util.Vector2i;

public class Solver extends Mob{
	
	private int anim = 0;
	private double xa = 0, ya = 0;
	private double speed = 0.8;
	private List<Node> path = null;
	int path_size = 0;
	
	public Solver(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.zombie_pig_forward;
		
	}
	 
	private void move(){
		if(path == null){
			
			Vector2i start = new Vector2i((int)(getX() + 7) >> 4, ((int)getY() + 6) >> 4);
			Vector2i goal = new Vector2i(30, 43);
			path = level.findPath(start, goal);
			path_size = path.size();
			
			
		}
		xa = ya = 0;
		
		if(path != null){
			if(path_size > 0){
				Vector2i v = path.get(path_size - 1).tile;
				if(x < v.getX() << 4) xa += speed;
				if(x > v.getX() << 4) xa -= speed;
				if(y < v.getY() << 4) ya += speed;
				if(y > v.getY() << 4) ya -= speed;
				
				if (Math.floor(x) == v.getX() << 4) xa = 0;
				if (Math.floor(y) == v.getY() << 4) ya = 0;
				
				if(Math.floor(x) == v.getX() << 4 && Math.floor(y) == v.getY() << 4)
					path_size--;
			}
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
		screen.renderMob((int)(x),(int)(y - 19), this);
	}
}