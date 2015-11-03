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
    public static Tile spawn_flower            = new SpawnGrassTile(Sprite.spawn_flower);
    public static Tile spawn_water             = new SpawnWaterTile(Sprite.spawn_water);
    public static Tile spawn_sign              = new SpawnSignTile(Sprite.spawn_sign);
    public static Tile spawn_teleporter        = new SpawnTeleporterTile(Sprite.spawn_teleporter);
    
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