package juego.graphics;

public class Sprite {
    
    public final int SIZE;
    private int x, y;
    private int width, height;
    public int[] pixels;
    private SpriteSheet sheet;
    
    // Some sprites
    
    public static Sprite grass = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite tall_grass = new Sprite(16, 2, 0, SpriteSheet.tiles);
    public static Sprite normal_flower = new Sprite(16, 3, 0, SpriteSheet.tiles);
    public static Sprite pretty_flower1 = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static Sprite pretty_flower2 = new Sprite(16, 5, 0, SpriteSheet.tiles);
    public static Sprite rock = new Sprite(16, 6, 0, SpriteSheet.tiles);
    public static Sprite tree = new Sprite(32, 0, 1, SpriteSheet.tiles);
    
    // Spawn level sprites
    
    public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_water = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_sign = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_flower = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_left = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_right = new Sprite(16, 2, 1, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_up = new Sprite(16, 1, 2, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_down = new Sprite(16, 2, 2, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_up_left = new Sprite(16, 0, 3, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_up_right = new Sprite(16, 1, 3, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_down_left = new Sprite(16, 3, 3, SpriteSheet.spawn_level);
    public static Sprite spawn_water_ledge_down_right = new Sprite(16, 3, 0, SpriteSheet.spawn_level);
    public static Sprite spawn_teleporter = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
    
    // Rock level sprites
    
    public static Sprite rock_floor = new Sprite(16, 0, 0, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_0 = new Sprite(16, 1, 0, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_1 = new Sprite(16, 2, 0, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_2 = new Sprite(16, 3, 0, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_3 = new Sprite(16, 1, 1, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_5 = new Sprite(16, 3, 1, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_6 = new Sprite(16, 1, 2, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_7 = new Sprite(16, 2, 2, SpriteSheet.rock_level);
    public static Sprite rock_lava_ledge_8 = new Sprite(16, 3, 2, SpriteSheet.rock_level);
    public static Sprite rock_lava_normal = new Sprite(16, 0, 1, SpriteSheet.rock_level);
    public static Sprite rock_lava_bubbles = new Sprite(16, 2, 1, SpriteSheet.rock_level);
    public static Sprite rock_wall_center_up = new Sprite(16, 4, 0, SpriteSheet.rock_level);
    public static Sprite rock_wall_LU = new Sprite(16, 1, 3, SpriteSheet.rock_level);
    public static Sprite rock_wall_center_left = new Sprite(16, 4, 1, SpriteSheet.rock_level);
    public static Sprite rock_wall_RU = new Sprite(16, 2, 3, SpriteSheet.rock_level);
    public static Sprite rock_wall_center_right = new Sprite(16, 5, 1, SpriteSheet.rock_level);
    public static Sprite rock_teleporter = new Sprite(16, 0, 2, SpriteSheet.rock_level);
    public static Sprite rock_rock = new Sprite(16, 0, 3, SpriteSheet.rock_level);
    
    // Player sprites
    
    public static Sprite player_forward = new Sprite(32, 0, 4, SpriteSheet.tiles);
    public static Sprite player_backward = new Sprite(32, 0, 5, SpriteSheet.tiles);
    public static Sprite player_right = new Sprite(32, 0, 6, SpriteSheet.tiles);
    public static Sprite player_left = new Sprite(32, 0, 7, SpriteSheet.tiles);
    
    // Player animation sprites
    
    public static Sprite player_forward_1 = new Sprite(32, 1, 4, SpriteSheet.tiles);
    public static Sprite player_forward_2 = new Sprite(32, 2, 4, SpriteSheet.tiles);
    public static Sprite player_backward_1 = new Sprite(32, 1, 5, SpriteSheet.tiles);
    public static Sprite player_backward_2 = new Sprite(32, 2, 5, SpriteSheet.tiles);
    public static Sprite player_right_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
    public static Sprite player_right_2 = new Sprite(32, 2, 6, SpriteSheet.tiles);
    public static Sprite player_left_1 = new Sprite(32, 1, 7, SpriteSheet.tiles);
    public static Sprite player_left_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);
    
    // Zombie pig sprites
    
    public static Sprite zombie_pig_forward = new Sprite(32, 0, 0, SpriteSheet.mobs);
    public static Sprite zombie_pig_backward = new Sprite(32, 0, 1, SpriteSheet.mobs);
    public static Sprite zombie_pig_right = new Sprite(32, 0, 2, SpriteSheet.mobs);
    public static Sprite zombie_pig_left = new Sprite(32, 0, 3, SpriteSheet.mobs);
    
    // Zombie pig animation sprites
    
    public static Sprite zombie_pig_forward1 = new Sprite(32, 1, 0, SpriteSheet.mobs);
    public static Sprite zombie_pig_forward2 = new Sprite(32, 2, 0, SpriteSheet.mobs);
    public static Sprite zombie_pig_backward1 = new Sprite(32, 1, 1, SpriteSheet.mobs);
    public static Sprite zombie_pig_backward2 = new Sprite(32, 2, 1, SpriteSheet.mobs);
    public static Sprite zombie_pig_right1 = new Sprite(32, 1, 2, SpriteSheet.mobs);
    public static Sprite zombie_pig_right2 = new Sprite(32, 2, 2, SpriteSheet.mobs);
    public static Sprite zombie_pig_left1 = new Sprite(32, 1, 3, SpriteSheet.mobs);
    public static Sprite zombie_pig_left2 = new Sprite(32, 2, 3, SpriteSheet.mobs);
    
    // Pokemon trainer sprites
    
    public static Sprite pokemon_trainer_forward = new Sprite(32, 3, 0, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_backward = new Sprite(32, 3, 1, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_right = new Sprite(32, 3, 2, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_left = new Sprite(32, 3, 3, SpriteSheet.mobs);
    
    // Pokemon trainer animation sprites
    
    public static Sprite pokemon_trainer_forward1 = new Sprite(32, 4, 0, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_forward2 = new Sprite(32, 5, 0, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_backward1 = new Sprite(32, 4, 1, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_backward2 = new Sprite(32, 5, 1, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_right1 = new Sprite(32, 4, 2, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_right2 = new Sprite(32, 5, 2, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_left1 = new Sprite(32, 4, 3, SpriteSheet.mobs);
    public static Sprite pokemon_trainer_left2 = new Sprite(32, 5, 3, SpriteSheet.mobs);
    
    // Projectile Sprites here:
    public static Sprite wizard_projectile_fire = new Sprite(16, 0, 0 , SpriteSheet.wizard_projectile);
    public static Sprite arrow = new Sprite(16, 1, 0 , SpriteSheet.wizard_projectile);
    public static Sprite wizard_projectile = new Sprite(16, 2 , 0 , SpriteSheet.wizard_projectile);
    public static Sprite mob_projectile = new Sprite(16, 0, 0 , SpriteSheet.mob_projectile);
    
    // Particles
    
    public static Sprite particle_normal = new Sprite(3, 0xffffff);
    public static Sprite particle_wizard_p = new Sprite(3, 0xFF0F65FF);
    public static Sprite particle_wizard_p_fire = new Sprite(3, 0xFFFF3B00);
    
    // Animations/more complex particles
    
    public static Sprite teleporter_particles = new Sprite(16, 32, 6, 4, SpriteSheet.tiles);
    public static Sprite fire_specialAbility = new Sprite(16, 32, 8, 4, SpriteSheet.tiles);
    
    // Just the void sprite
    
    public static Sprite voidSprite = new Sprite(16, 0x3F87D9);
    
    // Creates a square sized sprite from a spritesheet
    public Sprite(int size, int x, int y, SpriteSheet sheet){
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.width = size;
        this.height = size;
        // Coordinates of our sprite
        this.x = x * size;
        this.y = y * size;
        
        this.sheet = sheet;
        load();
    }
    
    // Creates a rectangle sized sprite from a spritesheet
    public Sprite(int width, int height, int x, int y, SpriteSheet sheet){
        SIZE = (width == height ? width : -1);
        pixels = new int[width * height];
        this.width = width;
        this.height = height;
        // Coordinates of our sprite
        this.x = x * width;
        this.y = y * height;
        
        this.sheet = sheet;
        load();
    }
    
    // Creates a rectangle sprite in a certain color
    public Sprite(int width, int height, int color){
    	SIZE = (width == height ? width : -1);
    	this.width = width;
    	this.height = height;
    	pixels = new int[width * height];
    	setColor(color);
    }
    
    // Creates a square sprite in a certain color
    public Sprite(int size, int color){
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }
    
    // Copies the pixels from something into this sprite
    public Sprite(int[] pixels, int width, int height){
    	SIZE = (width == height ? width : -1);
    	this.width = width;
    	this.height = height;
    	this.pixels = new int[pixels.length];
    	for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
    }
    
    // Creates a sprite array out of a spritesheet
    public static Sprite[] split(SpriteSheet sheet) {
    	int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
    	Sprite[] sprites = new Sprite[amount];
    	int current = 0;
    	int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
    	
		for(int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++){
			for(int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++){
				
				for(int y = 0; y < sheet.SPRITE_HEIGHT; y++){
					for(int x = 0; x < sheet.SPRITE_WIDTH; x++){
						int x0 = x + xp * sheet.SPRITE_WIDTH;
						int y0 = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[x0 + y0 * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
	}
    
    // Rotates a sprite
    public static Sprite rotate(Sprite sprite, double angle){
    	return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }
    
    // Changes the pixel array of a sprite so it's rotated
    private static int[] rotate(int[] pixels, int width, int height, double angle){
    	int[] result = new int[width * height];
    	
    	double nx_x = rot_x(-angle, 1.0, 0.0);
    	double nx_y = rot_y(-angle, 1.0, 0.0);
    	double ny_x = rot_x(-angle, 0.0, 1.0);
    	double ny_y = rot_y(-angle, 0.0, 1.0);
    	
    	double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
    	double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
    	
    	for(int y = 0; y < height; y++){
    		double x1 = x0;
    		double y1 = y0;
    		for(int x = 0; x < width; x++){
    			int ix = (int) x1;
    			int iy = (int) y1;
    			int col = 0;
    			if(ix < 0 || ix >= width || iy < 0 || iy >= height) col = 0xff00ff50;
    			else col = pixels[ix + iy * width];
    			result[x + y * width] = col;
    			x1 += nx_x;
    			y1 += nx_y;
    		}
    		x0 += ny_x;
    		y0 += ny_y;
    	}
    	
    	return result;
    }
    
    // Calculates rotation in x
    private static double rot_x(double angle, double x, double y){
    	double cos = Math.cos(angle - Math.PI / 2);
    	double sin = Math.sin(angle - Math.PI / 2);
    	return x * cos + y * -sin;
    }
    
    // Calculates rotation in y
    private static double rot_y(double angle, double x, double y){
    	double cos = Math.cos(angle - Math.PI / 2);
    	double sin = Math.sin(angle - Math.PI / 2);
    	return x * sin + y * cos;
    }
    
    private void setColor(int color){
        for(int i = 0; i < width * height; i++){
            pixels[i] = color;
        }
    }
    
    public int getWidth(){
    	return width;
    }
    
    public int getHeight(){
    	 return height;
    }
    
    private void load(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                // Finds the place in the SpriteSheet of the Sprite
                // we are looking for and extracts it in pixels
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
        }
    }    
}
