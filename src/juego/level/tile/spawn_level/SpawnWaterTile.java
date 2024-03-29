package juego.level.tile.spawn_level;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.tile.Tile;

public class SpawnWaterTile extends Tile {

    public SpawnWaterTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4, y << 4, this);
    }
    
    public boolean walkable(){
        return false;
    }
    
}
