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
    
    // Projectile Sprites here:
    public static Sprite wizard_projectile = new Sprite(16, 2 , 0 , SpriteSheet.wizard_projectile);
    
    // Particles
    
    public static Sprite particle_normal = new Sprite(3, 0xffffff);
    public static Sprite particle_wizard_p = new Sprite(3, 0xFF0F65FF);
    
    public static Sprite teleporter_particles = new Sprite(16, 32, 6, 4, SpriteSheet.tiles);
    
    // Just the void sprite
    
    public static Sprite voidSprite = new Sprite(16, 0x3F87D9);
    
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
    
    public Sprite(int width, int height, int x, int y, SpriteSheet sheet){
        SIZE = -1;
        pixels = new int[width * height];
        this.width = width;
        this.height = height;
        // Coordinates of our sprite
        this.x = x * width;
        this.y = y * height;
        
        this.sheet = sheet;
        load();
    }
    
    public Sprite(int width, int height, int color){
    	SIZE = -1;
    	this.width = width;
    	this.height = height;
    	pixels = new int[width * height];
    	setColor(color);
    }
    
    public Sprite(int size, int color){
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
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
