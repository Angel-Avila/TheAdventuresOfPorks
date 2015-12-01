package juego.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    private String path;
    public final int SIZE;
    public final int SPRITE_WIDTH;
    public final int SPRITE_HEIGHT;
    private int width, height;
    public int[] pixels;
    
    public static SpriteSheet tiles = new SpriteSheet("/res/textures/sheets/spritesheet.png", 256);
    public static SpriteSheet spawn_level = new SpriteSheet("/res/textures/sheets/spawn_level.png", 64);
    public static SpriteSheet wizard_projectile = new SpriteSheet("/res/textures/sheets/projectiles/wizard.png", 48);
    public static SpriteSheet mob_projectile = new SpriteSheet("/res/textures/sheets/projectiles/mob.png", 48);
    public static SpriteSheet mobs = new SpriteSheet("/res/textures/sheets/mobs.png", 256);
    public static SpriteSheet rock_level = new SpriteSheet("/res/textures/sheets/rock_level.png", 96);
    
    @ Deprecated
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize){
    	int xx = x * spriteSize;
    	int yy = y * spriteSize;
    	int w = width * spriteSize;
    	int h = height * spriteSize;
    	if(width == height) SIZE = width;
    	else SIZE = -1;
    	SPRITE_WIDTH = w;
    	SPRITE_HEIGHT = h;
    	pixels = new int[w * h];
    	for(int y0 = 0; y0 < h; y0++){
    		int yp = yy + y0;
    		for(int x0 = 0; x0 < w; x0++){
    			int xp = xx + x0;
    			pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
    		}
    	}
    }
    
    // Creates a square sized spritesheet
    public SpriteSheet(String path, int size){
        this.path = path;
        SIZE = size;
        SPRITE_WIDTH = size;
        SPRITE_HEIGHT = size;
        pixels = new int[SIZE * SIZE];
        load();
    }
    
 // Creates a rectangle sized spritesheet
    public SpriteSheet(String path, int width, int height){
        this.path = path;
        SIZE = -1;
        SPRITE_WIDTH = width;
        SPRITE_HEIGHT = height;
        pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
        load();
    }
     
    public int getWidth(){
    	return width;
    }
    
    public int getHeight(){
    	return height;
    }
    
    public int[] getPixels(){
    	return pixels;
    }
    
    private void load(){
        try {
            // Load our image into pixels array
        	System.out.print("Trying to load: " + path + "...");
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            System.out.println("succeded!");
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace(); 
        } catch (Exception e) {
            System.err.println(" failed!");
        }
    }
}