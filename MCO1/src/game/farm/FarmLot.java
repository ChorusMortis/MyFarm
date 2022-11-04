package game.farm;

import game.tile.Tile;

/**
 * Holds the tiles that the players will be working on.
 */
public class FarmLot {
    private Tile tiles; // TODO: in MCO2, update to some data structure later for multiple tiles
    
    /**
     * Creates a farm lot and initializes it with a new Tile.
     * @see Tile
     */
    public FarmLot() {
        this.tiles = new Tile();
    }

    /**
     * Creates a farm lot and initializes it with the given Tile.
     * @see Tile
     * @param tiles
     */
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
