package game.tile;

/**
 * Holds data for tile actions/options.
 */
public enum TileActionData {
    /**
     * Data for plowing a tile.
     */
    PLOW(0, 0.5),

    /**
     * Data for planting a crop. The money cost and the experience yield
     * depend on the planted crop.
     */
    PLANT(0, 0),

    /**
     * Data for harvesting a crop. The money cost and the experience yield
     * depend on the harvested crop.
     */
    HARVEST(0, 0),

    /**
     * Data for watering a crop.
     */
    WATER(0, 0.5),

    /**
     * Data for fertilizing a crop.
     */
    FERTILIZE(10, 4),

    /**
     * Data for mining a rock.
     */
    MINE(50, 15),

    /**
     * Data for digging.
     */
    DIG(7, 2);

    private double moneyCost;
    private double expYield;

    /**
     * Stores data for a tile action/option.
     * @param moneyCost   Money required for performing the action.
     * @param expYield    Experience gained for performing the action.
     */
    private TileActionData(double moneyCost, double expYield) {
        this.moneyCost = moneyCost;
        this.expYield = expYield;
    }

    public double getMoneyCost() {
        return moneyCost;
    }
    public double getExpYield() {
        return expYield;
    }
}
