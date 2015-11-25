package juego.level.tile.rock_level;

import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.level.tile.Tile;

public class RockLavaTile extends Tile{
    public RockLavaTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4, y << 4, this);
    }
    
    public boolean walkable(){
        return false;
    }
    
}
