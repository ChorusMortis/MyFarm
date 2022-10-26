package game.tile;

import game.crop.*;

public class Tile {
    private Crop plantedCrop;
    private boolean plowed;
    private boolean occupied;
    // private boolean rock; // not used as of MCO1

    public TileActionReport plow() {
        
        return new TileActionReport();
    }

    // not used as of MCO1
    // public boolean generateRock() {
    //     return false;
    // }
}
