package juego.level.tile;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.tile.spawn_level.SpawnGrassTile;
import juego.level.tile.spawn_level.SpawnSignTile;
import juego.level.tile.spawn_level.SpawnTeleporterTile;
import juego.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
    
    public Sprite sprite;
    
    public static Tile grass = new GrassTile(Sprite.grass);
    public static Tile tall_grass = new BushTile(Sprite.tall_grass);
    public static Tile normal_flower = new Normal_FlowerTile(Sprite.normal_flower);
    public static Tile rock = new RockTile(Sprite.rock);
    public static Tile voidTile = new VoidTile(Sprite.voidSprite);
    
    // Spawn tiles:
    
    public static Tile spawn_grass                  = new SpawnGrassTile(Sprite.spawn_grass);
    public static Tile spawn_water_ledge_right      = new SpawnGrassTile(Sprite.spawn_water_ledge_right);
    public static Tile spawn_water_ledge_left       = new SpawnGrassTile(Sprite.spawn_water_ledge_left);
    public static Tile spawn_water_ledge_up         = new SpawnGrassTile(Sprite.spawn_water_ledge_up);
    public static Tile spawn_water_ledge_down       = new SpawnGrassTile(Sprite.spawn_water_ledge_down);
    public static Tile spawn_water_ledge_up_left    = new SpawnGrassTile(Sprite.spawn_water_ledge_up_left);
    public static Tile spawn_water_ledge_up_right   = new SpawnGrassTile(Sprite.spawn_water_ledge_up_right);
    public static Tile spawn_water_ledge_down_left  = new SpawnGrassTile(Sprite.spawn_water_ledge_down_left);
    public static Tile spawn_water_ledge_down_right = new SpawnGrassTile(Sprite.spawn_water_ledge_down_right);
    public static Tile spawn_flower       	        = new SpawnGrassTile(Sprite.spawn_flower);
    public static Tile spawn_water            	    = new SpawnWaterTile(Sprite.spawn_water);
    public static Tile spawn_sign             	    = new SpawnSignTile(Sprite.spawn_sign);
    public static Tile spawn_teleporter       	    = new SpawnTeleporterTile(Sprite.spawn_teleporter);
    
    // Rock tiles:
    
    public static Tile rock_floor 		  	 = new GrassTile(Sprite.rock_floor);
    public static Tile rock_lava_ledge_0  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_0);
    public static Tile rock_lava_ledge_1  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_1);
    public static Tile rock_lava_ledge_2  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_2);
    public static Tile rock_lava_ledge_3  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_3);
    public static Tile rock_lava_ledge_5  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_5);
    public static Tile rock_lava_ledge_6   	 = new SpawnWaterTile(Sprite.rock_lava_ledge_6);
    public static Tile rock_lava_ledge_7  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_7);
    public static Tile rock_lava_ledge_8  	 = new SpawnWaterTile(Sprite.rock_lava_ledge_8);
    public static Tile rock_lava_normal  	 = new SpawnWaterTile(Sprite.rock_lava_normal);
    public static Tile rock_lava_bubbles 	 = new SpawnWaterTile(Sprite.rock_lava_bubbles);
    public static Tile rock_wall_center_up	 = new RockTile(Sprite.rock_wall_center_up);
    public static Tile rock_wall_LU			 = new RockTile(Sprite.rock_wall_LU);
    public static Tile rock_wall_center_right= new RockTile(Sprite.rock_wall_center_right);
    public static Tile rock_wall_RU			 = new RockTile(Sprite.rock_wall_RU);
    public static Tile rock_wall_center_left = new RockTile(Sprite.rock_wall_center_left);
    public static Tile rock_rock          	 = new RockTile(Sprite.rock_rock);
    public static Tile rock_teleporter    	 = new SpawnTeleporterTile(Sprite.rock_teleporter);
    
    // Spawn level colors for the level pngs
    
    public static final int col_spawn_grass                 = 0xFF00FF00;
    public static final int col_spawn_water_ledge_right     = 0xFF633E3F;
    public static final int col_spawn_water_ledge_left      = 0xFF613E3F;
    public static final int col_spawn_water_ledge_up        = 0xFF623E3F;
    public static final int col_spawn_water_ledge_down      = 0xFF643E3F;
    public static final int col_spawn_water_ledge_up_left   = 0xFFFF00FF;
    public static final int col_spawn_water_ledge_up_right  = 0xFFFE00FF;
    public static final int col_spawn_water_ledge_down_left = 0xFFFD00FF;
    public static final int col_spawn_water_ledge_down_right= 0xFFFC00FF;
    public static final int col_spawn_flower                = 0xFFFFFF00;
    public static final int col_spawn_water                 = 0xFF0000FF;
    public static final int col_spawn_sign                  = 0xFFFFFFFF;
    public static final int col_spawn_teleporter            = 0xFF00FFFF;
    
 // Rock level colors for the level pngs
    
    public static final int col_rock_floor                 		= 0xFFD09888;
    public static final int col_rock_lava_ledge_0               = 0xFF803020;
    public static final int col_rock_lava_ledge_1               = 0xFF803021;
    public static final int col_rock_lava_ledge_2               = 0xFF803022;
    public static final int col_rock_lava_ledge_3               = 0xFF803023;
    public static final int col_rock_lava_ledge_5               = 0xFF803024;
    public static final int col_rock_lava_ledge_6               = 0xFF803025;
    public static final int col_rock_lava_ledge_7               = 0xFF803026;
    public static final int col_rock_lava_ledge_8               = 0xFF803027;
    public static final int col_rock_lava_normal              	= 0xFFC83000;
    public static final int col_rock_lava_bubbles              	= 0xFFC88400;
    public static final int col_rock_teleporter        		    = 0xFF00FFFE; 
    public static final int col_rock_rock	        		    = 0xFF808080; 
    public static final int col_rock_wall_center_up    		    = 0xFFB200FE;
    public static final int col_rock_wall_center_right 		    = 0xFFB200FD;
    public static final int col_rock_wall_center_left  		    = 0xFFB200FC;
    public static final int col_rock_wall_LU		 		    = 0xFFB200FB;
    public static final int col_rock_wall_RU		 		    = 0xFFB200FA;
    
    public Tile(Sprite sprite){
        this.sprite = sprite;
    }
    
    public void render(int x, int y, Screen screen){
        
    }
    
    public boolean solid(){
        return false;
    }
    
    public boolean walkable(){
    	return true;
    }
    
}