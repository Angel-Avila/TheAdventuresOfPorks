package juego.entity.mob;

import java.util.List;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.Node;
import juego.util.Vector2i;

public class Star extends Mob {
	
	private int anim = 0;
	private double xa = 0, ya = 0;
	private double speed = 1.15;
	private List<Node> path = null;
	private int time = 0;
	
	public Star(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		position = new Vector2i(x, y);
		damage = .5;
		this.actualHealth = this.maxHealth = 200;
		sprite = Sprite.zombie_pig_forward;
	}
	 
	private void move(){
		xa = ya = 0;
		// If there are players in the level
		if(level.players.size() > 0){
			// We get their position (with an offset to match where they really graphcally are)
			int px = (int)level.getPlayerAt(0).getX() + 7;
			int py = (int)level.getPlayerAt(0).getY() + 6;
			// Get the vectors of position from where he is and where we are
			Vector2i start = new Vector2i((int)(getX() + 7) >> 4, ((int)getY() + 6) >> 4);
			Vector2i goal = new Vector2i(px >> 4, py >> 4);
			// And we calculate a path to get to them about 12 times per/sec so we are always looking for the shortest 
			// path and updating it
			if(time % 5 == 0) path = level.findPath(start, goal);
			if(path != null){
				if(path.size() > 0){
					Vector2i v = path.get(path.size() - 1).tile;
					if(x < v.getX() << 4) xa += speed;
					if(x > v.getX() << 4) xa -= speed;
					if(y < v.getY() << 4) ya += speed;
					if(y > v.getY() << 4) ya -= speed;
					
					// We round dowm because we are moving with doubles, if we didn't round down, the mob
					// would go crazy moving left/right or up/down slightly to try to have the same X and/or
					// Y values but failing because of the speed. He wouldn't be able to have exactly the 
					// same value so we have to do this to prevent him from becoming insane and moving weirdly.
					if (Math.abs(Math.floor(x) - (v.getX() << 4)) < 2) xa = 0;
					if (Math.abs(Math.floor(y) - (v.getY() << 4)) < 2) ya = 0;
				}
			}
			
			// Move, change sprites
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
	}
	
	public void update() {
		checkHit();
		
		if(this.actualHealth <= 0)
			die();
		
		move();
		position.set(getTileX(), getTileY());
		
		if (anim < 7500)
			anim++;
		else
			anim = 0;
		
		if (time < 7500)
			time++;
		else
			time = 0;
		
		// change sprites
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
