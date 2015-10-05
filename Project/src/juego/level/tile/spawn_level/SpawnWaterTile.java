package src.juego.level.tile.spawn_level;

import src.juego.graphics.Screen;
import src.juego.graphics.Sprite;
import src.juego.level.tile.Tile;

public class SpawnWaterTile extends Tile {

    public SpawnWaterTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4, y << 4, this);
    }
    
    public boolean solid(){
        return true;
    }
    
}
