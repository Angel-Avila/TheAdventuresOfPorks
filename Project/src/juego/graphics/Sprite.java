package src.juego.graphics;

public class Sprite {
    
    public final int SIZE;
    private int x, y;
    public int[] pixels;
    private SpriteSheet sheet;
    
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
    
    // Projectile Sprites here:
    public static Sprite wizard_projectile = new Sprite(16, 2 , 0 , SpriteSheet.wizard_projectile);
    
    // Just the void sprite
    
    public static Sprite voidSprite = new Sprite(16, 0x3F87D9);
    
    public Sprite(int size, int x, int y, SpriteSheet sheet){
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        // Coordinates of our sprite
        this.x = x * size;
        this.y = y * size;
        
        this.sheet = sheet;
        load();
    }
    
    public Sprite(int size, int color){
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }
    
    private void setColor(int color){
        for(int i = 0; i < SIZE * SIZE; i++){
            pixels[i] = color;
        }
    }
    
    private void load(){
        for(int y = 0; y < SIZE; y++){
            for(int x = 0; x < SIZE; x++){
                // Finds the place in the SpriteSheet of the Sprite
                // we are looking for and extracts it in pixels
                pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
        }
    }
    
}
