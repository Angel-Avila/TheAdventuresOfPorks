package juego.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class SpriteSheet {
    private String path;
    public final int SIZE;
    public int[] pixels;
    
    public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
    public static SpriteSheet spawn_level = new SpriteSheet("/textures/sheets/spawn_level.png", 64);
    public static SpriteSheet wizard_projectile = new SpriteSheet("/textures/sheets/projectiles/wizard.png", 48);
    public static SpriteSheet mobs = new SpriteSheet("/textures/sheets/mobs.png", 256);
    
    public SpriteSheet(String path, int size){
        this.path = path;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }
    
    private void load(){
        try {
            // Load our image into pixels array
            BufferedImage image = ImageIO.read
            (SpriteSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}