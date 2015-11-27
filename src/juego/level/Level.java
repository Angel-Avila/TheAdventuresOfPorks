package juego.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import juego.Game;
import juego.entity.Entity;
import juego.entity.mob.Chaser;
import juego.entity.mob.Dummy;
import juego.entity.mob.Player;
import juego.entity.mob.PokemonTrainer;
import juego.entity.mob.Star;
import juego.entity.particle.Particle;
import juego.entity.projectile.Projectile;
import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.tile.Tile;
import juego.sound.Sound;
import juego.util.Vector2i;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	private int time = 0;
	private boolean teleporting = false;
	public static Level spawn = new SpawnLevel("/res/levels/spawn.png");
	public static Level labyrinth = new SpawnLevel("/res/levels/labyrinth.png");
	public static Level test = new TestLevel("/res/levels/test.png");
	public static Level rock = new RockLevel("/res/levels/rock.png");

	public List<Entity> entities = new ArrayList<Entity>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Particle> particles = new ArrayList<Particle>();

	public List<Player> players = new ArrayList<>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {

		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return +1;
			if (n1.fCost > n0.fCost)
				return -1;
			return 0;
		}

	};

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {

	}

	protected void loadLevel(String path) {

	}

	// Updates all of our ArrayLists and removes them if they should be removed
	public void update(Game game, Player player, Screen screen) {

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

		// If the player is in the spawnlevel on the teleporter from the left
		// side he goes to the labyrinth
		if (player.level == spawn) {
			if (player.position.equals(new Vector2i(28, 24))) {
				time++;
				teleporting = true;
				if (time % 120 == 0) {
					changeLevel(labyrinth, game, player, screen);
					return;
				}
			} else if (player.position.equals(new Vector2i(31, 24))) {
				time++;
				teleporting = true;
				if (time % 120 == 0) {
					changeLevel(rock, game, player, screen);
					return;
				}
			} else {
				time = 0;
				teleporting = false;
			}
		}
		// But if he is in the labyrinth and manages to get to the other side,
		// he returns to the spawn
		else if (player.level == labyrinth && getClientPlayer().getTileY() == 44) {
			time++;
			teleporting = true;
			if (time % 120 == 0) {
				changeLevel(spawn, game, player, screen);
				return;
			}
		}
		else if (player.level == rock && 
				getClientPlayer().getTileX() == 82 && getClientPlayer().getTileY() == 7){
			time++;
			teleporting = true;
			if (time % 120 == 0) {
				changeLevel(labyrinth, game, player, screen);
				return;
			}
		}
		else {
			time = 0;
			teleporting = false;
		}

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).position.equals(player.position))
				player.hitEntity(entities.get(i).damage);

		}

		remove();
	}

	// Checks all our arraylists to check if an entity should be removed and it
	// removes it from the arraylist if it is
	// and that ultimately makes it so it isn't rendered because we only render
	// the entities that are on the arraylists
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved())
				entities.remove(i);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}

		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
		}
	}

	public void removeAll() {
		for (int i = 0; i < entities.size(); i++) {
			entities.remove(i);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.remove(i);
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.remove(i);
		}

		for (int i = 0; i < players.size(); i++) {
			players.remove(i);
		}
	}

	/**
	 * Gets our tiles eith the getTile method and renders them to the screen,
	 * then checks all our arraylists and renders all the entities inside
	 * 
	 * @param xScroll
	 *            x location of the player
	 * @param yScroll
	 *            y location of the player
	 * @param screen
	 *            our screen object to call the setOffset method of the Screen
	 *            class Remember, we render pixel by pixel, 16 x 16, to make a
	 *            tile
	 */
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		// The corner pins
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}

		// Shows the teleporting "energy" under our player when he's about to
		// teleport
		if (teleporting)
			screen.renderSprite((int) getClientPlayer().getX() - 2, (int) getClientPlayer().getY() - 23,
					Sprite.teleporter_particles, true);

	}

	public void renderMiniMap(int xScroll, int yScroll, Screen screen) {
		screen.setMiniMapOffset(xScroll, yScroll);
		int x0 = xScroll;
		int x1 = (xScroll + screen.miniMapWidth + 1);
		int y0 = yScroll;
		int y1 = (yScroll + screen.miniMapHeight + 1);

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).renderMiniMap(x, y, screen);
			}
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).renderMiniMap(screen);
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).renderMiniMap(screen);
		}
	}

	// Adds entities to our arraylists
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			entities.add(e);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	/**
	 * In order to change level we remove all the entities we have from the
	 * current one to avoid null pointer exceptions and then we proceed to go to
	 * the next level where we add the level entities we should.
	 */
	public void changeLevel(Level level, Game game, Player player, Screen screen) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).remove();
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).remove();
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).remove();
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).remove();
		}

		for(int i = 0; i < screen.miniMapPixels.length; i++){
			screen.miniMapPixels[i] = 0;
		}
		
		// We set the level value from game and player to the new level
		game.level = level;
		player.level = level;

		// Change music according to the level
		if (level == Level.labyrinth) {
			player.setXY(game.playerSpawn_labyrinth.getX(), game.playerSpawn_labyrinth.getY());
			Sound.encounter.loop();
		} else if (level == Level.spawn) {
			player.setXY(game.playerSpawn_spawnLevel.getX(), game.playerSpawn_spawnLevel.getY());
			Sound.spawnMusic.loop();
		} else if (level == Level.rock) {
			player.setXY(9 << 4, 70 << 4);
			Sound.vsRed.loop();
		}

		// This checks which entities are removed(in this case all of them) and
		// it removes them from the game
		remove();
		// We change the "removed" value from player to false and we add it now
		// to the level
		player.unRemove();
		level.add(player);
		// We add the mobs that should spawn in a certain level, depending on
		// which it is
		level.addLevelMobs();
	}

	public List<Integer> getEntitiesIndex(Player e, int radius) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int) entity.getX();
			int y = (int) entity.getY();

			int dx = (int) Math.abs(x - e.getX());
			int dy = (int) Math.abs(y - e.getY());

			double dt = Math.sqrt((dx * dx) + (dy * dy));

			if (dt <= radius)
				result.add(i);
		}
		return result;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int) entity.getX();
			int y = (int) entity.getY();

			int dx = (int) Math.abs(x - e.getX());
			int dy = (int) Math.abs(y - e.getY());

			double dt = Math.sqrt((dx * dx) + (dy * dy));

			if (dt <= radius)
				result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int) player.getX();
			int y = (int) player.getY();

			int dx = (int) Math.abs(x - e.getX());
			int dy = (int) Math.abs(y - e.getY());

			double dt = Math.sqrt((dx * dx) + (dy * dy));

			if (dt <= radius)
				result.add(player);
		}
		return result;
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<>();
		List<Node> closedList = new ArrayList<>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);

		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<>();

				// Only node where this happens would be the start. This is
				// gonna backtrace it and return the path
				while (current.parent != null) {
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
				if (i == 4)
					continue; // Because it would be the node we're actually on
				int x = current.tile.getX();
				int y = current.tile.getY();

				// These will help us check the adjacent tiles
				int xi = (i % 3) - 1; // -1, 0 or 1
				int yi = (i / 3) - 1; // -1, 0 or 1

				// Tile we're checking now in the loop
				Tile at = getTile(x + xi, y + yi);

				// If the tile is null or is solid we don't wanna check it
				if (at == null)
					continue;
				if (!at.walkable())
					continue;

				// We check the diagonals and if the 2 tiles next to them aren't
				// walkable we ignore them, because
				// if the mob tries this path he'll just get stuck.
				if ((i == 0 && !getTile(x + xi + 1, y + yi).walkable() && !getTile(x + xi, y + yi + 1).walkable())
						|| (i == 2 && !getTile(x + xi - 1, y + yi).walkable()
								&& !getTile(x + xi, y + yi + 1).walkable())
						|| (i == 6 && !getTile(x + xi + 1, y + yi).walkable()
								&& !getTile(x + xi, y + yi - 1).walkable())
						|| (i == 8 && !getTile(x + xi - 1, y + yi).walkable()
								&& !getTile(x + xi, y + yi - 1).walkable()))
					continue;

				// We make a vector from the Tile we're checking
				Vector2i atVector = new Vector2i(x + xi, y + yi);

				// We calculate the costs from a node made at the tile we're
				// checking
				// If it's taking a diagonal it returns a smaller value than 1
				// so our A* algorithm prefers
				// diagonals, this makes the movement more human than him taking
				// diagonals at the end
				double gCost = current.gCost + (getDistance(current.tile, atVector) == 1 ? 1 : .95);
				double hCost = getDistance(atVector, goal);
				// We create the node we just commented about
				Node node = new Node(atVector, current, gCost, hCost);

				// If the vector is in the closed list we don't wanna add it
				if (vecInList(closedList, atVector) && gCost >= node.gCost)
					continue;

				// If the vector isn't in the openList, we will add it
				if (!vecInList(openList, atVector) || gCost < node.gCost)
					openList.add(node);

			}
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list)
			if (n.tile.equals(vector))
				return true;

		return false;
	}

	private double getDistance(Vector2i v1, Vector2i v2) {
		double dx = v1.getX() - v2.getX();
		double dy = v1.getY() - v2.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	public void damageMobAt(int i, int damage) {
		entities.get(i).hitEntity(damage);
	}

	public void addLevelMobs() {
		if (this == spawn) {
			Random random = new Random();
			int x = 0;
			int y = 0;
			for (int i = 0; i < 20; i++) {
				x = random.nextInt(29);
				y = random.nextInt(29);
				// In the spawnlevel we spawn the dummies in walkable tiles and
				// at least 1 tile away from our player
				if (getTile(x + 15, y + 15).walkable() && getDistance(new Vector2i(x + 15, y + 15),
						new Vector2i((int) players.get(0).getX(), (int) players.get(0).getY())) > 16)
					add(new Dummy(x + 15, y + 15));
				else
					i--;
			}
		} else if (this == labyrinth) {
			add(new Star(36, 40));
			add(new Star(44, 41));
			add(new Star(30, 42));
			add(new Star(41, 22));
			add(new Star(15, 24));
			add(new Star(30, 40));
			add(new Chaser(32, 19));
			add(new Chaser(30, 40));
			add(new Chaser(17, 30));
			add(new PokemonTrainer(42, 18));
		} else if (this == rock){
			add(new Star(8, 7));
			add(new Star(82, 71));
			add(new Star(67, 45));
			add(new Star(82, 7));
			add(new Star(44, 20));
			add(new Chaser(19, 49));
			add(new Chaser(43, 23));
			add(new Chaser(47, 65));
			add(new Chaser(66, 58));
			add(new Chaser(22, 17));
			add(new PokemonTrainer(81, 8));
			Random random = new Random();
			int x = 0;
			int y = 0;
			for (int i = 0; i < 25; i++) {
				x = random.nextInt(75);
				y = random.nextInt(65);
				// In the level we spawn the dummies in walkable tiles and
				// at least 3 tile away from our player
				if (getTile(x + 8, y + 7).walkable() && getDistance(new Vector2i(x + 8, y + 7),
						new Vector2i((int) players.get(0).getTileX(), (int) players.get(0).getTileY())) > 3)
					add(new Dummy(x + 8, y + 7));
				else
					i--;
			}
			for (int i = 0; i < 10; i++) {
				x = random.nextInt(75);
				y = random.nextInt(65);
				// In the level we spawn the chasers in walkable tiles and
				// at least 6 tile away from our player
				// +8 in x and +7 in y because we want it to spawn inside the rectangle of the map. If we don't add that up
				// the mob could spawn in the outsides of the map and we don't want that since he wouldn't be able to enter 
				// the level
				if (getTile(x + 8, y + 7).walkable() && getDistance(new Vector2i(x + 8, y + 7),
						new Vector2i((int) players.get(0).getTileX(), (int) players.get(0).getTileY())) > 6)
					add(new Chaser(x + 8, y + 7));
				else
					i--;
			}
			
			for(int i = 0; i < 12; i++){
				x = random.nextInt(74);
				y = random.nextInt(64);
				// In the level we spawn the trainers in walkable tiles and
				// at least 9 away from our player
				// +8 in x and +7 in y because we want it to spawn inside the rectangle of the map. If we don't add that up
				// the mob could spawn in the outsides of the map and we don't want that since he wouldn't be able to enter 
				// the level
				if (getTile(x + 8, y + 7).walkable() && getDistance(new Vector2i(x + 8, y + 7),
						new Vector2i((int) players.get(0).getTileX(), (int) players.get(0).getTileY())) > 9)
					add(new PokemonTrainer(x + 8, y + 7));
				else
					i--;
			}
		}
	}

	/**
	 * @param x
	 *            position of our entity + the direction in which our projectile
	 *            is heading
	 * @param y
	 *            position of our entity + the direction in which our projectile
	 *            is heading
	 * @param size
	 *            of the entity
	 * @return if the tile our object is moving to is solid or not
	 */
	public boolean tileProjectileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid())
				solid = true;
		}
		return solid;
	}

	/**
	 * We also check if we hit entities and we apply the damage with "hitEntity"
	 * 
	 * @param x
	 *            position of our entity + the direction in which our projectile
	 *            is heading
	 * @param y
	 *            position of our entity + the direction in which our projectile
	 *            is heading
	 * @param p
	 *            our projectile
	 * @return if we hit an entity or not
	 */
	public boolean entityProjectileCollision(int x, int y, int xOffset, int yOffset, Projectile p) {
		boolean isHit = false;
		for (int i = 0; i < entities.size(); i++) {
			if ((x - 5) < entities.get(i).getX() + 10 && (x - 5) > entities.get(i).getX() - 8
					&& (y + 1) < entities.get(i).getY() + 12 && (y + 1) > entities.get(i).getY() - 12) {
				isHit = true;
				entities.get(i).hitEntity(p.damage);
				return isHit;
			}
		}
		return isHit;
	}
	
	/**
	 * We also check if we hit players and we apply the damage with "hitEntity"
	 * 
	 * @param x
	 *            position of our player + the direction in which our projectile
	 *            is heading
	 * @param y
	 *            position of our player + the direction in which our projectile
	 *            is heading
	 * @param p
	 *            our projectile
	 * @return if we hit an entity or not
	 */
	public boolean playerProjectileCollision(int x, int y, int xOffset, int yOffset, Projectile p) {
		boolean isHit = false;
		for (int i = 0; i < players.size(); i++) {
			if ((x - 5) < players.get(i).getX() + 10 && (x - 5) > players.get(i).getX() - 8
					&& (y + 1) < players.get(i).getY() + 12 && (y + 1) > players.get(i).getY() - 12) {
				isHit = true;
				players.get(i).hitEntity(p.damage);
				return isHit;
			}
		}
		return isHit;
	}

	/**
	 * Grass = 0xFF00FF00 Flower = 0xFFFFFF00 Bush = 0xFF007F00 Rock =
	 * 0xFF7F7F00 This method checks our tiles int array that's filled with
	 * colors(in hexadecimal value) and returns the corresponding tile.
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (this == spawn || this == labyrinth)
			if (tiles[x + y * width] == Tile.col_spawn_grass)
				return Tile.spawn_grass;
		if (tiles[x + y * width] == Tile.col_spawn_flower)
			return Tile.spawn_flower;
		if (tiles[x + y * width] == Tile.col_spawn_sign)
			return Tile.spawn_sign;
		if (tiles[x + y * width] == Tile.col_spawn_teleporter)
			return Tile.spawn_teleporter;
		if (tiles[x + y * width] == Tile.col_spawn_water)
			return Tile.spawn_water;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_down)
			return Tile.spawn_water_ledge_down;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_down_left)
			return Tile.spawn_water_ledge_down_left;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_down_right)
			return Tile.spawn_water_ledge_down_right;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_left)
			return Tile.spawn_water_ledge_left;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_right)
			return Tile.spawn_water_ledge_right;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_up)
			return Tile.spawn_water_ledge_up;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_up_left)
			return Tile.spawn_water_ledge_up_left;
		if (tiles[x + y * width] == Tile.col_spawn_water_ledge_up_right)
			return Tile.spawn_water_ledge_up_right;
		else if (tiles[x + y * width] == Tile.col_rock_floor)
			return Tile.rock_floor;
		if (tiles[x + y * width] == Tile.col_rock_wall_center_up)
			return Tile.rock_wall_center_up;
		if (tiles[x + y * width] == Tile.col_rock_wall_center_right)
			return Tile.rock_wall_center_right;
		if (tiles[x + y * width] == Tile.col_rock_wall_center_left)
			return Tile.rock_wall_center_left;
		if (tiles[x + y * width] == Tile.col_rock_wall_LU)
			return Tile.rock_wall_LU;
		if (tiles[x + y * width] == Tile.col_rock_wall_RU)
			return Tile.rock_wall_RU;
		if (tiles[x + y * width] == Tile.col_rock_rock)
			return Tile.rock_rock;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_0)
			return Tile.rock_lava_ledge_0;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_1)
			return Tile.rock_lava_ledge_1;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_2)
			return Tile.rock_lava_ledge_2;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_3)
			return Tile.rock_lava_ledge_3;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_5)
			return Tile.rock_lava_ledge_5;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_6)
			return Tile.rock_lava_ledge_6;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_7)
			return Tile.rock_lava_ledge_7;
		if (tiles[x + y * width] == Tile.col_rock_lava_ledge_8)
			return Tile.rock_lava_ledge_8;
		if (tiles[x + y * width] == Tile.col_rock_lava_normal)
			return Tile.rock_lava_normal;
		if (tiles[x + y * width] == Tile.col_rock_lava_bubbles)
			return Tile.rock_lava_bubbles;
		if (tiles[x + y * width] == Tile.col_rock_teleporter)
			return Tile.rock_teleporter;
		return Tile.voidTile;
	}
}
