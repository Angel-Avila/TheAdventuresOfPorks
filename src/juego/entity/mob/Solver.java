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
	private Vector2i goal;
	private int path_size = 0;
	private boolean possible = true;
	
	public Solver(int x, int y, Vector2i goal){
		this.x = x << 4;
		this.y = y << 4;
		this.goal = goal;
		damage = .01;
		position = new Vector2i(x, y);
		this.actualHealth = this.maxHealth = 40;
		sprite = Sprite.zombie_pig_forward;
	}
	 
	private void move(){
		// If he hasn't "thought" of a path yet,
		if(path == null && possible){
			// He uses 2 vectors consisting of where he is and of where he wants to get
			Vector2i start = new Vector2i((int)(getX() + 7) >> 4, ((int)getY() + 6) >> 4);
			// Then he finds the shortest path posible with the A* search algorithm
			path = level.findPath(start, goal);
			// We store the size of the path in an integer variable if it's possible to get there
			if(path != null)
				path_size = path.size();
			else
				possible = false;
		}
		xa = ya = 0;
		
		if(path != null){
			if(path_size > 0){
				// And we use it to get the position of the next tile we have to go to in order to get to our goal.
				// Since the last element of the path list is the closest one to us, we simply get the position vector
				// of that tile, we get there with the next lines of code
				Vector2i v = path.get(path_size - 1).tile;
				if(x < v.getX() << 4) xa += speed;
				if(x > v.getX() << 4) xa -= speed;
				if(y < v.getY() << 4) ya += speed;
				if(y > v.getY() << 4) ya -= speed;
				
				if (Math.floor(x) == v.getX() << 4) xa = 0;
				if (Math.floor(y) == v.getY() << 4) ya = 0;
				
				// And when we do, we substract one from path size so in the next call of this mehod, we get the 
				// position vector of the next tile closest to us until th path_size is 0, meaning we got to our goal.
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
		checkHit();
		
		if(this.actualHealth <= 0)
			remove();
		
		move();
		position.set(getTileX(), getTileY());
		
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