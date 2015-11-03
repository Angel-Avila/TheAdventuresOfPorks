package juego.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import juego.entity.mob.Chaser;
import juego.entity.mob.Dummy;

public class SpawnLevel extends Level{
    
    public SpawnLevel(String path) {
        super(path);
    }
    
    protected void loadLevel(String path){    
        try{
            BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
            int w = width = image.getWidth();
            int h = height = image.getHeight();
            tiles = new int[w * h];
            image.getRGB(0, 0, w, h, tiles, 0, w);
        } catch (IOException e) {
           e.printStackTrace();
            System.out.println("Exception, could not load level file!");
        }
        
        for(int i = 0; i < 10; i++){
        	if(i % 2 == 0) add(new Dummy(18 + i * 3, 40));
        	else	       add(new Chaser(18 + i * 3, 40));
        }/*
        add(new Dummy(18, 40));
        add(new Chaser(21, 40));
        add(new Dummy(24, 40));
        add(new Chaser(27, 40));
        add(new Dummy(30, 40));
        add(new Chaser(33, 40));
        add(new Dummy(36, 40));
        add(new Chaser(39, 40));
        add(new Dummy(42, 40));
        add(new Chaser(44, 40));*/
    }
    
    
    protected void generateLevel(){
        
    }
    
}
