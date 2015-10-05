package src.juego.entity.mob;

import src.juego.Game;
import src.juego.entity.projectile.Projectile;
import src.juego.entity.projectile.WizardProjectile;
import src.juego.graphics.Screen;
import src.juego.graphics.Sprite;
import src.juego.input.Keyboard;
import src.juego.input.Mouse;

public class Player extends Mob{
    
    private Keyboard input;
    private Sprite sprite;
    private int anim = 0;
    private boolean walking = false;
    
    private int fireRate = 0;
    
    public Player(Keyboard input){
        this.input = input;
        sprite = Sprite.player_forward;
    }
    
    public Player(int x, int y, Keyboard input){
        this.x = x;
        this.y = y;
        this.input = input;
        dir = 2;
        sprite = Sprite.player_forward;
        fireRate = WizardProjectile.FIRE_RATE;
    }
    
    public void update(){
        int xa = 0, ya = 0;
        if(anim < 7500) anim++;
        else anim = 0;
        if(input.up) ya-=2;
        if(input.down) ya+=2;
        if(input.left) xa-=2;
        if(input.right) xa+=2;
        
        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        }
        else
            walking = false;
        
        if(fireRate > 0) fireRate--;
        
        clear();
        updateShooting();
    }
    
    private void clear() {
		for(int i = 0; i < level.getProjectiles().size(); i++){
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}

	private void updateShooting() {	
    	if(Mouse.getB() == 1 && fireRate <= 0){
    		double dx = Mouse.getX() - Game.getWindowWidth() / 2;
    		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
    		double dir = Math.atan2(dy, dx);
    		shoot(x, y, dir);
    		fireRate = WizardProjectile.FIRE_RATE;
    	}
	}

	public void render(Screen screen){
        if(dir == 0) {
            sprite = Sprite.player_backward;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_backward_1;
                else sprite = Sprite.player_backward_2; 
            }
        }
        if(dir == 1) {
            sprite = Sprite.player_right;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_right_1;
                else sprite = Sprite.player_right_2; 
            }
        }
        if(dir == 2) {
            sprite = Sprite.player_forward;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_forward_1;
                else sprite = Sprite.player_forward_2; 
            }
        }
        if(dir == 3) {
            sprite = Sprite.player_left;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_left_1;
                else sprite = Sprite.player_left_2; 
            }
        }
        screen.renderPlayer(x - 8, y - 16, sprite);        
    }
}
