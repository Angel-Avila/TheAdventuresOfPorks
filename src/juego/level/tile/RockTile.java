package juego.level.tile;

import juego.graphics.Screen;
import juego.graphics.Sprite;

class RockTile extends Tile {

    public RockTile(Sprite sprite) {
        super(sprite);
    }
    
    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4, y << 4, this);
    }
    
    public boolean solid(){
        return true;
    }
    
    public boolean walkable(){
    	return false;
    }
}
