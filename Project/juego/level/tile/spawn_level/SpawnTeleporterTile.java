package juego.level.tile.spawn_level;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.tile.Tile;

/**
 *
 * @author Ã�ngel
 */
public class SpawnTeleporterTile extends Tile {

    public SpawnTeleporterTile(Sprite sprite) {
        super(sprite);
    }
    
    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4, y << 4, this);
    }
    
}
