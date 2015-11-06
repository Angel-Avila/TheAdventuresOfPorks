package juego.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import juego.entity.Entity;
import juego.entity.mob.Chaser;
import juego.entity.mob.Dummy;
import juego.entity.mob.Player;
import juego.entity.mob.Solver;
import juego.entity.mob.Star;
import juego.entity.particle.Particle;
import juego.entity.projectile.Projectile;
import juego.graphics.Screen;
import juego.level.tile.Tile;
import juego.util.Vector2i;

public class Level {
    
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;
    public static Level spawn = new SpawnLevel("/res/levels/spawn.png");
    public static Level labyrinth = new SpawnLevel("/res/levels/labyrinth.png");
    
    public List<Entity> entities = new ArrayList<Entity>();
    public List<Projectile> projectiles = new ArrayList<Projectile>();
    public List<Particle> particles = new ArrayList<Particle>();
    
    public List<Player> players = new ArrayList<>();
    
    private Comparator<Node> nodeSorter = new Comparator<Node>(){

		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return +1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
    	
    };
    
    
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
    	
    	for (int i = 0; i < players.size(); i++) {
    		players.get(i).update();
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
    	
    	for (int i = 0; i < players.size(); i++) {
    		if(players.get(i).isRemoved()) players.remove(i);
		}
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
        
        for (int i = 0; i < players.size(); i++) {
       		players.get(i).render(screen);
		}
    }
    
    // Adds entities to our arraylists
    public void add(Entity e){
    	e.init(this);
    	if(e instanceof Particle){
    		particles.add((Particle) e);
    	} else if(e instanceof Projectile) {
    		projectiles.add((Projectile) e);
    	} else if(e instanceof Player){
    		players.add((Player) e);
    	} else{
    		entities.add(e);
    	}
    }
    
    public List<Projectile> getProjectiles(){
    	return projectiles;
    }
    
    public List<Player> getPlayers(){
    	return players;
    }
    
    public Player getPlayerAt(int index){
    	return players.get(index);
    }
    
    public Player getClientPlayer(){
    	return players.get(0);
    }
    
    public List<Entity> getEntities(Entity e, int radius){
    	List<Entity> result = new ArrayList<>();
    	for(int i = 0; i < entities.size(); i++){
    		Entity entity = entities.get(i);
    		int x = (int)entity.getX();
    		int y = (int)entity.getY();
    		
    		int dx = (int)Math.abs(x - e.getX());
    		int dy = (int)Math.abs(y - e.getY());
    		
    		double dt = Math.sqrt((dx * dx) + (dy * dy));
    		
    		if(dt <= radius) result.add(entity);
    	}
    	return result;
    }
    
    public List<Player> getPlayers(Entity e, int radius){
    	List<Player> result = new ArrayList<>();
    	for(int i = 0; i < players.size(); i++){
    		Player player = players.get(i);
    		int x = (int)player.getX();
    		int y = (int)player.getY();
    		
    		int dx = (int)Math.abs(x - e.getX());
    		int dy = (int)Math.abs(y - e.getY());
    		
    		double dt = Math.sqrt((dx * dx) + (dy * dy));
    		
    		if(dt <= radius) result.add(player);
    	}
    	return result;
    }
    
    public List<Node> findPath(Vector2i start, Vector2i goal){
    	List<Node> openList = new ArrayList<>();
    	List<Node> closedList = new ArrayList<>();
    	Node current = new Node(start, null, 0, getDistance(start, goal));
    	openList.add(current);
    	
    	while(openList.size() > 0){
    		Collections.sort(openList, nodeSorter);
    		current = openList.get(0);
    		if(current.tile.equals(goal)){
    			List<Node> path = new ArrayList<>();
    			
    			// Only node where this happens would be the start. This is gonna
    			// backtrace it and return the path
    			while(current.parent != null){
    				path.add(current);
    				current = current.parent;
    			}
    			openList.clear();
    			closedList.clear();
    			return path;
    		}
    		openList.remove(current);
    		closedList.add(current);
    		
    		// Checks the adjacent nodes(8)
    		for (int i = 0; i < 9; i++) {
				if( i == 4) continue; // Because it would be the node we're actually on
				int x = current.tile.getX();
				int y = current.tile.getY();
				
				// These will help us check the adjacent tiles
				int xi = (i % 3) - 1; // -1, 0 or 1
				int yi = (i / 3) - 1; // -1, 0 or 1	
				
				// Tile we're checking now in the loop
				Tile at = getTile(x + xi, y + yi); 
				
				// If the tile is null or is solid we don't wanna check it
				if(at == null) continue;
				if(!at.walkable()) continue;
				
				// We make a vector from the Tile we're checking
				Vector2i atVector = new Vector2i(x + xi, y + yi);
				
				// We calculate the costs from a node made at the tile we're checking
				double gCost = current.gCost + getDistance(current.tile, atVector);
				double hCost = getDistance(atVector, goal);
				// We create the node we just commented about
				Node node = new Node(atVector, current, gCost, hCost);
				
				// If the vector is in the closed list we don't wanna add it
				if(vecInList(closedList, atVector) && gCost >= node.gCost) continue;
				
				// If the vector isn't in the openList, we will add it
				if(!vecInList(openList, atVector) || gCost < node.gCost) openList.add(node);
				
 			}
    	}
    	closedList.clear();
    	return null;
    }
    
    private boolean vecInList(List<Node> list, Vector2i vector){
    	for(Node n : list)
    		if(n.tile.equals(vector)) return true;
    	
    	return false;
    }
    
    private double getDistance(Vector2i v1, Vector2i v2){
    	double dx = v1.getX() - v2.getX();
    	double dy = v1.getY() - v2.getY();
    	return Math.sqrt((dx * dx) + (dy * dy));
    }
    
    public void addLevelMobs(){
    	if(this == spawn){
    		add(new Dummy(30, 40));
    		add(new Dummy(33, 40));
    		add(new Dummy(27, 40));
    	}
    	else if(this == labyrinth){
    		add(new Star(30, 40));
    		add(new Chaser(30, 40));
    		add(new Solver(42, 18));
    	}
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
