package juego.level;

import java.util.ArrayList;
import java.util.List;

import juego.entity.Entity;
import juego.entity.particle.Particle;
import juego.entity.projectile.Projectile;
import juego.entity.spawner.Spawner;
import juego.graphics.Screen;
import juego.level.tile.Tile;

public class Level {
    
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;
    public static Level spawn = new SpawnLevel("/levels/spawn.png");
    
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Projectile> projectiles = new ArrayList<Projectile>();
    private List<Particle> particles = new ArrayList<Particle>();
    
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
    
    // Updates all of our ArrayLists and removes them if they should be removed
    public void update(){
    	for (int i = 0; i < entities.size(); i++) {
    		entities.get(i).update();
		}
    	
    	for (int i = 0; i < projectiles.size(); i++) {
    		projectiles.get(i).update();
		}
    	
    	for (int i = 0; i < particles.size(); i++) {
    		particles.get(i).update();
		}
        remove();
    }
    
    // Checks all our arraylists to check if an entity should be removed and it removes it from the arraylist if it is
    // and that ultimately makes it so it isn't rendered because we only render the entities that are on the arraylists
    private void remove(){
    	for (int i = 0; i < entities.size(); i++) {
    		if(entities.get(i).isRemoved()) entities.remove(i);
		}
    	
    	for (int i = 0; i < projectiles.size(); i++) {
    		if(projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
    	
    	for (int i = 0; i < particles.size(); i++) {
    		if(particles.get(i).isRemoved()) particles.remove(i);
		}
    }
    
    public List<Projectile> getProjectiles(){
    	return projectiles;
    }
    
    private void time(){
        
    }
    
    /**
     * @param x position of our entity + the direction in which it's heading
     * @param y position of our entity + the direction in which it's heading
     * @param size of the entity
     * @return if the tile our object is moving to is solid or not
     */
    public boolean tileProjectileCollision(int x, int y, int size, int xOffset, int yOffset){
    	boolean solid = false;
    	for (int c = 0; c < 4; c++) {
    		int xt = (x - c % 2 * size + xOffset) >> 4;
    		int yt = (y - c / 2 * size + yOffset) >> 4;
    		if(getTile(xt, yt).solid()) solid = true;
		}
        return solid;
    }
    
    /**
     * Gets our tiles eith the getTile method and renders them to the screen, then checks all our arraylists and 
     * renders all the entities inside
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
        
        for (int i = 0; i < particles.size(); i++) {
       		particles.get(i).render(screen);
		}
    }
    
    // Adds entities to our arraylists
    public void add(Entity e){
    	e.init(this);
    	if(e instanceof Particle){
    		particles.add((Particle) e);
    	}else if(e instanceof Projectile) {
    		projectiles.add((Projectile) e);
    	} else{
    		entities.add(e);
    	}
    }
    
    /** Grass  = 0xFF00FF00
     *  Flower = 0xFFFFFF00
     *  Bush   = 0xFF007F00
     *  Rock   = 0xFF7F7F00
     *  This method checks our tiles int array that's filled with colors(in hexadecimal value) and returns the
     *  corresponding tile.
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
