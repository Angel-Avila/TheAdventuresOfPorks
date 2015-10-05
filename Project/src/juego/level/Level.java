package src.juego.level;

import java.util.ArrayList;
import java.util.List;

import src.juego.entity.Entity;
import src.juego.entity.projectile.Projectile;
import src.juego.graphics.Screen;
import src.juego.level.tile.Tile;

public class Level {
    
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;
    public static Level spawn = new SpawnLevel("/levels/spawn.png");
    
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Projectile> projectiles = new ArrayList<Projectile>();
    
    public Level(int width, int height){
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }
    
    public Level(String path){
        loadLevel(path);
        generateLevel();
    }
    
    protected void generateLevel(){
        
    }
    
    protected void loadLevel(String path){
        
    }
    
    public void update(){
    	for (int i = 0; i < entities.size(); i++) {
    		entities.get(i).update();
		}
    	
    	for (int i = 0; i < projectiles.size(); i++) {
    		projectiles.get(i).update();
		}
        
    }
    
    public List<Projectile> getProjectiles(){
    	return projectiles;
    }
    
    private void time(){
        
    }
    /**
     * 
     * @param xScroll x location of the player
     * @param yScroll y location of the player
     * @param screen our screen object to call the setOffset method of the Screen class
     * Remember, we render pixel by pixel, 16 x 16, to make a tile
     */
    public void render(int xScroll, int yScroll, Screen screen){
        screen.setOffset(xScroll, yScroll);
        // The corner pins
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width + 16) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;
        
        for(int y = y0; y < y1; y++){
            for(int x = x0; x < x1; x++){
                getTile(x, y).render(x, y, screen); 
            }
        }
        
        for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
        
        for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
    }
    
    public void add(Entity e){
    	entities.add(e);
    }
    
    public void addProjectile(Projectile p){
    	projectiles.add(p);
    }
    
    /** Grass  = 0xFF00FF00
     *  Flower = 0xFFFFFF00
     *  Bush   = 0xFF007F00
     *  Rock   = 0xFF7F7F00
     */    
    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height ) return Tile.voidTile;
        if(tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass;
        if(tiles[x + y * width] == Tile.col_spawn_flower) return Tile.spawn_flower;
        if(tiles[x + y * width] == Tile.col_spawn_sign) return Tile.spawn_sign;
        if(tiles[x + y * width] == Tile.col_spawn_teleporter) return Tile.spawn_teleporter;
        if(tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_down) return Tile.spawn_water_ledge_down;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_down_left) return Tile.spawn_water_ledge_down_left;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_down_right) return Tile.spawn_water_ledge_down_right;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_left) return Tile.spawn_water_ledge_left;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_right) return Tile.spawn_water_ledge_right;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_up) return Tile.spawn_water_ledge_up;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_up_left) return Tile.spawn_water_ledge_up_left;
        if(tiles[x + y * width] == Tile.col_spawn_water_ledge_up_right) return Tile.spawn_water_ledge_up_right;
        return Tile.voidTile;
    }
}
