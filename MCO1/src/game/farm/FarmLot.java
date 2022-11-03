package game.farm;

import game.tile.Tile;

public class FarmLot {
    private Tile tiles; // TODO: in MCO2, update to some data structure later for multiple tiles
    
    public FarmLot() {
        this.tiles = new Tile();
    }

    public FarmLot(Tile tiles) {
        this.tiles = tiles;
    }

    public Tile getTiles() {
        return tiles;
    }
    public void setTiles(Tile tiles) {
        this.tiles = tiles;
    }
}
