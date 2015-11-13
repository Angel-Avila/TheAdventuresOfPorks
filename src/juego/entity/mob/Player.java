package juego.entity.mob;

import juego.Game;
import juego.entity.projectile.WizardProjectile;
import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.graphics.ui.UILabel;
import juego.graphics.ui.UIManager;
import juego.graphics.ui.UIPanel;
import juego.input.Keyboard;
import juego.input.Mouse;
import juego.util.Vector2i;

public class Player extends Mob{
    
	private String name;
    private Keyboard input;
    private Sprite sprite;
    private int anim = 0;
    
    public Vector2i position;
    
    private int fireRate = 0;
    
    private UIManager ui;
    
    public Player(Keyboard input){
        this.input = input;
        sprite = Sprite.player_forward;  
    }
    
    public Player(String name, int x, int y, Keyboard input){
    	this.name = name;
        this.x = x;
        this.y = y;
        this.input = input;
        position = new Vector2i(x, y);
        dir = Direction.DOWN;
        sprite = Sprite.player_forward;
        fireRate = WizardProjectile.FIRE_RATE;
        
        // We start our UI objects
        ui = Game.getUIManager();
        
        // This creates the gray panel on the right side of the screen
        UIPanel panel = (UIPanel) new UIPanel(new Vector2i(240 * 3, 0), new Vector2i(60 * 3, 168 * 3)).setColor(0x4f4f4f);
        // And this adds it to our UI
        ui.addPanel(panel);
        // Then we add a label to our panel
        panel.addComponent(new UILabel(new Vector2i(40, 180), name).setColor(0xbbbbbb));
    }
    
    public void update(){/*
    	int xi = (int)((x + 7) / 16);
    	int yi = (int)((y + 6) / 16);
    	System.out.println(xi + ", " + yi + ", " + level.getTile(xi, yi).walkable());*/
        if(anim < 7500) anim++;
        else anim = 0;
        
        double speed = 1.25;
        double xa = 0, ya = 0;
                
        if(input.up) ya -= speed;
        if(input.down) ya += speed;
        if(input.left) xa -= speed;
        if(input.right) xa += speed;
        
        
        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        }
        else
            walking = false;
        
        position.set(getTileX(), getTileY());
        
        // Takes 1 from firerate because the player only shoots in the updateShooting() method if fireRate is <= 0
        // and after shooting it resets the fireRate int value to the declared FIRE_RATE value in WizardProjectile
        if(fireRate > 0) fireRate--;
        
        updateShooting();
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getName(){
    	return name;
    }
    
	private void updateShooting() {	
		// If the player is clicking the left click and the fireRate is <= 0 
    	if(Mouse.getB() == 1 && fireRate <= 0){
    		// Gets the x and y coordinates from where the user wants to shoot relative to the window, not the map
    		double dx = Mouse.getX() - Game.getWindowWidth() / 2;
    		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
    		// Uses the atan function of y / x to get the angle. The - 10 is an offset I did to center the projectile
    		double dir = Math.atan2(dy, dx - 10);
    		// Shoots to the desired direction
    		shoot(x, y, dir);
    		// Resets the fireRate so it can shoot again
    		fireRate = WizardProjectile.FIRE_RATE;
    	}
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}

	 public Player getClientPlayer(){
	    	return this;
	    }
	
	// The anim % 20 > 10 is just an animation to alternate between the 2 walking sprites, one with the right leg
	// and one with the left leg
	public void render(Screen screen){
        if(dir == Direction.UP) {
            sprite = Sprite.player_backward;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_backward_1;
                else sprite = Sprite.player_backward_2; 
            }
        }
        if(dir == Direction.RIGHT) {
            sprite = Sprite.player_right;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_right_1;
                else sprite = Sprite.player_right_2; 
            }
        }
        if(dir == Direction.DOWN) {
            sprite = Sprite.player_forward;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_forward_1;
                else sprite = Sprite.player_forward_2; 
            }
        }
        if(dir == Direction.LEFT) {
            sprite = Sprite.player_left;
            if(walking){
                if(anim % 20 > 10) sprite = Sprite.player_left_1;
                else sprite = Sprite.player_left_2; 
            }
        }
        
        // Renders the player with a slight offset
        screen.renderMob((int)(x), (int)(y - 19), sprite);        
    }
}
