package src.juego.graphics;


import java.util.Random;

import src.juego.entity.projectile.Projectile;
import src.juego.level.tile.Tile;

public class Screen {
    
    public int width, height;
    public int[] pixels;
    public final int MAP_SIZE = 64;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    
    public int xOffset, yOffset;
    
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();
    
    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        
        /*
        for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++){
            tiles[i] = random.nextInt(0xffffff);
        }random shit*/
    }
    
    public void clear(){
        for(int i = 0; i < pixels.length; i++)
            pixels[i] = 0;
    }
    /*
    public void render(int xOffset, int yOffset){
        for(int y = 0; y < height; y++){
            int yp = y + yOffset;
            if(yp < 0 || yp >= height) continue;
            for(int x = 0; x < width; x++){
                int xp = x + xOffset;
                if(xp < 0 || xp >= width) continue;
                pixels[xp + yp * width] = Sprite.grass.pixels[(x & (Sprite.grass.SIZE - 1)) + 
                        (y & (Sprite.grass.SIZE - 1)) * Sprite.grass.SIZE];
            }
        }
    } ye' old example method to render a screen full of pixels*/
    
    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
    	if(fixed){
	    	xp -= xOffset;
	    	yp -= yOffset;
    	}
    	for(int y = 0; y < sprite.getHeight(); y++){
    		int ya = y + yp;
    		for(int x = 0; x < sprite.getWidth(); x++){
    			int xa = x + xp;
    			if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
    			pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
    		}
    	}
    }
    
    /**
     * 
     * @param xp gives the x position of the tile on the map
     * @param yp gives the y position of the tile on the map
     * @param tile the tile to be rendered
     * 
     * y and x are the pixels up to rendering
     * ya and xa are the abosulte positions of x and y
     */  
    public void renderTile(int xp, int yp, Tile tile){
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < tile.sprite.SIZE; y++){
            int ya = y + yp;
            for(int x = 0; x < tile.sprite.SIZE; x++){
                int xa = x + xp;
                if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if(xa < 0) xa = 0;
                // X and Y in the sprite because they aren't offset, only
                // things in the screen get offset, not in the sprite
                pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }
    
    public void renderProjectile(int xp, int yp, Projectile p){
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++){
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++){
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height ) break;
				if (xa < 0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if(col != 0xff00ff50)
					pixels[xa + ya * width] = col;
			}
		}
	}
    
    public void renderPlayer(int xp, int yp, Sprite sprite){
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < 32; y++){
            int ya = y + yp;
            for(int x = 0; x < 16; x++){
                int xa = x + xp;
                if(xa < -16 || xa >= width || ya < 0 || ya >= height) break;
                if(xa < 0) xa = 0;				
                // X and Y in the sprite because they aren't offset, only
                // things in the screen get offset, not in the sprite
                int col = sprite.pixels[x + y * 32];
                if(col != 0xff00ff50)
                    pixels[xa + ya * width] = col;
            }
        }
    }
    
    public void setOffset(int xOffset, int yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}