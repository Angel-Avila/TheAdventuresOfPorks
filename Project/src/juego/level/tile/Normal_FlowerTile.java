package src.juego.level.tile;

import src.juego.graphics.Screen;
import src.juego.graphics.Sprite;

class Normal_FlowerTile extends Tile {

    public Normal_FlowerTile(Sprite sprite) {
        super(sprite);
    }
    
    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4, y << 4, this);
    }
}
