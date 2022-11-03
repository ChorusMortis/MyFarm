package game.tile;

public enum TileActionData {
    PLOW(0, 0.5),
    PLANT(0, 0),
    HARVEST(0, 0), // depends on the harvested crop
    WATER(0, 0.5),
    FERTILIZE(10, 4),
    MINE(50, 15),
    DIG(7, 2);

    private double moneyCost;
    private double expYield;

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
