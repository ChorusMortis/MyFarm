package game.crop;

public class Crop {
    // Crop description
    private CropName name;
    private CropType type;
    private int baseSeedCost;
    private int baseSellPrice;
    private double expYield;

    // Harvesting
    private int harvestAge;
    private int age = 0;
    private boolean harvestable;
    private boolean withered;
    private int minYield;
    private int maxYield;

    // Withering reasons
    private boolean overripe;
    private boolean lacksWater;
    private boolean lacksFertilizer;

    // Watering stats
    private int currentWater;
    private int waterNeeded;
    private int waterLimit;

    // Fertilizer
    private int currentFertilizer;
    private int fertilizerNeeded;
    private int fertilizerLimit;

    // Special mechanics
    private double premiumRate = 1.0;


    public int getRandomYield() {
        return (int)((Math.random() * (maxYield - minYield)) + minYield);
    }

    public void addAge(int amount) {
        age += amount;
    }

    public void addWater(int amount) {
        currentWater += amount;
    }

    public void addFertilizer(int amount) {
        currentFertilizer += amount;
    }

    // Convenience method for adding age
    public void grow() {
        age++;
    }

    // Convenience method for adding water
    public void water() {
        currentWater++;
    }

    // Convenience method for adding fertilizer
    public void fertilize() {
        currentFertilizer++;
    }

    public void nextDay() {
        grow(); // crop ages regardless of state

        if (!withered && age >= harvestAge) {
            if (currentWater < waterNeeded) {
                harvestable = false;
                withered = true;
                lacksWater = true;
            } else if (currentFertilizer < fertilizerNeeded) {
                harvestable = false;
                withered = true;
                lacksFertilizer = true;
            } else { // currentWater >= waterNeeded && currentFertilizer >= fertilizerNeeded
                harvestable = true;
            }
        }
    }

    // TODO: implement harvest()
    public HarvestCropReport harvest() {

        return new HarvestCropReport(0, 0, 0);
    }

    public Crop() {
    }

    public Crop(CropName name, CropType type, int baseSeedCost, int baseSellPrice, double expYield, int harvestAge,
            int minYield, int maxYield, int waterNeeded, int waterLimit, int fertilizerNeeded, int fertilizerLimit,
            double premiumRate) {
        this.name = name;
        this.type = type;
        this.baseSeedCost = baseSeedCost;
        this.baseSellPrice = baseSellPrice;
        this.expYield = expYield;
        this.harvestAge = harvestAge;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.waterNeeded = waterNeeded;
        this.waterLimit = waterLimit;
        this.fertilizerNeeded = fertilizerNeeded;
        this.fertilizerLimit = fertilizerLimit;
        this.premiumRate = premiumRate;
    }

    
}