package juego.graphics;

import juego.entity.Entity;
import juego.entity.mob.Chaser;
import juego.entity.mob.Mob;
import juego.entity.mob.Player;
import juego.entity.mob.Star;
import juego.entity.projectile.Projectile;
import juego.level.tile.RockTile;
import juego.level.tile.Tile;
import juego.level.tile.rock_level.RockLavaTile;
import juego.level.tile.spawn_level.SpawnGrassTile;
import juego.level.tile.spawn_level.SpawnSignTile;
import juego.level.tile.spawn_level.SpawnTeleporterTile;
import juego.level.tile.spawn_level.SpawnWaterTile;

public class Screen {

	public int width, height;
	public int miniMapWidth, miniMapHeight;
	public int[] pixels;
	public int[] miniMapPixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;

	public int xOffset, yOffset;
	public int xMMOffset, yMMOffset;

	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	// private Random random = new Random();

	private final int ALPHA_COL = 0xff00ff50;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		/*
		 * for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++){ tiles[i] =
		 * random.nextInt(0xffffff); }random shit
		 */
	}

	public Screen(int width, int height, int miniMapWidth, int miniMapHeight) {
		this.width = width;
		this.height = height;
		this.miniMapWidth = miniMapWidth;
		this.miniMapHeight = miniMapHeight;
		pixels = new int[width * height];
		miniMapPixels = new int[miniMapWidth * miniMapHeight];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}
	/*
	 * public void render(int xOffset, int yOffset){ for(int y = 0; y < height;
	 * y++){ int yp = y + yOffset; if(yp < 0 || yp >= height) continue; for(int
	 * x = 0; x < width; x++){ int xp = x + xOffset; if(xp < 0 || xp >= width)
	 * continue; pixels[xp + yp * width] = Sprite.grass.pixels[(x &
	 * (Sprite.grass.SIZE - 1)) + (y & (Sprite.grass.SIZE - 1)) *
	 * Sprite.grass.SIZE]; } } } ye' old example method to render a screen full
	 * of pixels
	 */

	// Renders the sprite where we want to in the map if it's not fixed, if it's
	// fixed renders it somewhere
	// relative to the screen, not to the map
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffFF00FF && col != 0xff7F007F)
					pixels[xa + ya * width] = color;
			}
		}
	}

	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
				if (col != 0xff00ff50)
					pixels[xa + ya * width] = col;
			}
		}
	}

	/**
	 * 
	 * @param xp
	 *            gives the x position of the tile on the map
	 * @param yp
	 *            gives the y position of the tile on the map
	 * @param tile
	 *            the tile to be rendered
	 * 
	 *            y and x are the pixels up to rendering ya and xa are the
	 *            abosulte positions of x and y
	 */
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				// X and Y in the sprite because they aren't offset, only
				// things in the screen get offset, not in the sprite
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}

	public void renderTileMiniMap(int xMM, int yMM, Tile tile) {
		int color = 0;
		if (tile instanceof SpawnGrassTile) {
			color = 0x4CFF00;
			if(tile.sprite == Sprite.spawn_flower)
				color = 0xFFC228;
			else if(tile.sprite == Sprite.rock_floor)
				color = 0xD09888;
		} 
		
		else if (tile instanceof SpawnWaterTile)
			color = 0x4800FF;
		
		else if (tile instanceof SpawnSignTile)
			color = 0x613E3F;
		
		else if (tile instanceof SpawnTeleporterTile)
			color = 0xFFD800;
		
		else if(tile instanceof RockLavaTile){
			color = 0xC83000;
			if(tile.sprite == Sprite.rock_lava_bubbles)
				color = 0xC88400;
		}
		
		else if(tile instanceof RockTile)
			color = 0x803021;
		
		xMM -= xMMOffset;
		yMM -= yMMOffset;

		if (xMM >= miniMapWidth || yMM >= miniMapHeight || yMM < 0 || xMM < 0)
			return;
		miniMapPixels[xMM + yMM * miniMapWidth] = color;
	}

	public void renderPlayerMinimap(Player player) {
		int xMM = player.getTileX() - xMMOffset;
		int yMM = player.getTileY() - yMMOffset;

		if (xMM >= miniMapWidth || yMM >= miniMapHeight || yMM < 0 || xMM < 0)
			return;
		miniMapPixels[xMM + yMM * miniMapWidth] = 0xB200FF;
	}

	public void renderMobMiniMap(Entity entity) {
		int xMM = entity.getTileX() - xMMOffset;
		int yMM = entity.getTileY() - yMMOffset;

		if (xMM >= miniMapWidth || yMM >= miniMapHeight || yMM < 0 || xMM < 0)
			return;
		miniMapPixels[xMM + yMM * miniMapWidth] = 0xff0000;
	}

	// Renders our projectile
	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if (col != ALPHA_COL)
					pixels[xa + ya * width] = col;
			}
		}
	}

	@Deprecated
	public void renderMob(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				// X and Y in the sprite because they aren't offset, only
				// things in the screen get offset, not in the sprite
				int col = sprite.pixels[x + y * 32];
				if (col != 0xff00ff50)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				// X and Y in the sprite because they aren't offset, only
				// things in the screen get offset, not in the sprite
				int col = mob.getSprite().pixels[x + y * 32];
				// We render the brain of Chaser and Star in different colors to
				// tell a difference easily
				if (mob instanceof Chaser) {
					if (col == 0xffD89F97)
						col = 0xff2D2AD3;
					else if (col == 0xffDDB4AC)
						col = 0xff2DD9D3;
				} else if (mob instanceof Star) {
					if (col == 0xffD89F97) // Brain color
						col = 0xffB200FF;
					else if (col == 0xffDDB4AC) // Light brain color
						col = 0xffFF00DC;
					// else if(col == 0xffCC0000) // Blood color
				}

				if (col != 0xff00ff50) {
					if (mob.isHit())
						// col = 0xffCC0000;
						col += 0xffD80000;
					// col += 0xff0000;

					pixels[xa + ya * width] = col;
				}
			}
		}
	}

	public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {

		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		for (int x = xp; x < xp + width; x++) {
			if (yp >= this.height || x < 0 || x >= this.width)
				continue;
			if (yp > 0)
				pixels[x + yp * this.width] = color;
			if (yp + height >= this.height)
				continue;
			if (yp + height > 0)
				pixels[x + (yp + height) * this.width] = color;
		}

		for (int y = yp; y <= yp + height; y++) {
			if (xp >= this.width || y < 0 || y >= this.height)
				continue;
			if (xp > 0)
				pixels[xp + y * this.width] = color;
			if (xp + width >= this.width)
				continue;
			if (xp + width > 0)
				pixels[(xp + width) + y * this.width] = color;
		}

	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void setMiniMapOffset(int xMMOffset, int yMMOffset) {
		this.xMMOffset = xMMOffset;
		this.yMMOffset = yMMOffset;
	}
}