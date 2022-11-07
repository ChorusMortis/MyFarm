package game.crop;

/**
 * A crop that can be planted and sold for profit.
 */
public class Crop {
    // Crop description
    private CropName name;
    private CropType type;
    private double baseSeedCost;
    private double baseSellPrice;
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

    /**
     * Creates a new crop.
     */
    public Crop() {
    }

    /**
     * Creates a new crop and initializes its data using set crop data.
     * @param cropData
     */
    public Crop(CropData cropData) {
        this.name = cropData.getName();
        this.type = cropData.getType();
        this.baseSeedCost = cropData.getBaseSeedCost();
        this.baseSellPrice = cropData.getBaseSellPrice();
        this.expYield = cropData.getExpYield();
        this.harvestAge = cropData.getHarvestAge();
        this.minYield = cropData.getMinYield();
        this.maxYield = cropData.getMaxYield();
        this.waterNeeded = cropData.getNeededWater();
        this.waterLimit = cropData.getWaterLimit();
        this.fertilizerNeeded = cropData.getNeededFertilizer();
        this.fertilizerLimit = cropData.getFertilizerLimit();
        this.premiumRate = cropData.getPremiumRate();
    }

    /**
     * Creates a new crop and initializes its data.
     * @param name               Name of the crop.
     * @param type               Type of the crop.
     * @param baseSeedCost       Base buy cost when purchasing.
     * @param baseSellPrice      Base sell price for each product sold.
     * @param expYield           Experience given when harvested.
     * @param harvestAge         Days before a crop can become harvestable.
     * @param minYield           Lower bound for products produced.
     * @param maxYield           Upper bound for products produced.
     * @param waterNeeded        Required water to become harvestable.
     * @param waterLimit         Upper bound for the water count.
     * @param fertilizerNeeded   Required fertilizer to become harvestable.
     * @param fertilizerLimit    Upper bound for the fertilizer count.
     * @param premiumRate        Premium for each piece sold.
     */
    public Crop(CropName name, CropType type, double baseSeedCost, double baseSellPrice, double expYield,
            int harvestAge, int minYield, int maxYield, int waterNeeded, int waterLimit, int fertilizerNeeded,
            int fertilizerLimit, double premiumRate) {
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

    /**
     * Increases the crop's age and updates its state, depending on its age,
     * current water and fertilizer count. If multiple withering conditions
     * are satisfied, only the first one checked reflects.
     */
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
            } else if (age > harvestAge) {
                harvestable = false;
                withered = true;
                overripe = true;
            } else { // currentWater >= waterNeeded && currentFertilizer >= fertilizerNeeded && age == harvestAge
                harvestable = true;
            }
        }
    }
    
    /**
     * Returns a harvest report containing the products produced and the
     * crop's experience yield. Does not calculate the profit when sold.
     * @return   A report containing the products produced and the crop's
     *           experience yield.
     * @see game.tile.Tile#harvest(double) Tile.harvest(double)
     * @see HarvestCropReport
     */
    public HarvestCropReport harvest() {
        int productsProduced = getRandomYield();
        return new HarvestCropReport(productsProduced, 0, expYield);
    }
    
    /**
     * Randomly generates a number between the crop's minimum and maximum
     * product yield, both inclusive.
     * @return   The randomly generated yield.
     */
    public int getRandomYield() {
        return (int)((Math.random() * (maxYield - minYield)) + minYield);
    }

    /**
     * Increases the crop's age in days.
     * @param amount   The amount of days to add to the crop's age.
     */
    public void addAge(int amount) {
        age += amount;
    }

    /**
     * Increases the crop's age by one day.
     * @see #addAge(int)
     */
    public void grow() {
        addAge(1);
    }

    /**
     * Increases the crop's water count until it reaches its water limit.
     * @param amount   The amount of water to add to the crop.
     */
    public void addWater(int amount) {
        currentWater += amount;
        if (currentWater > waterLimit) {
            currentWater = waterLimit;
        }
    }

    /**
     * Increases the crop's water count by one.
     * @see #addWater(int)
     */
    public void water() {
        addWater(1);
    }

    /**
     * Increases the crop's fertilizer count until it reaches its fertilizer
     * limit.
     * @param amount   The amount of fertilizer to add to the crop.
     */
    public void addFertilizer(int amount) {
        currentFertilizer += amount;
        if (currentFertilizer > fertilizerLimit) {
            currentFertilizer = fertilizerLimit;
        }
    }

    /**
     * Increases the crop's fertilizer count by one.
     * @see #addFertilizer(int)
     */
    public void fertilize() {
        addFertilizer(1);
    }

    /**
     * Displays the crop's attributes: name, type, age, current water count,
     * current fertilizer count, if it's harvestable or withered, and reason
     * for withering, if it is the latter.
     */
    public void printState() {
        var s = "Crop Name: " + name.getStringName() + "\n"
              + "Crop Type: " + type.getStringName() + "\n"
              + "Age: " + age + " / " + harvestAge + "\n"
              + "Water: " + currentWater + " / " + waterNeeded + "(" + waterLimit + ")" + "\n"
              + "Fertilizer: " + currentFertilizer + " / " + fertilizerNeeded + "(" + fertilizerLimit + ")" + "\n"
              + "Is harvestable: " + harvestable + "\n"
              + "Is withered: " + withered + "\n"
              + "Is overripe: " + overripe + "\n"
              + "Lacks water: " + lacksWater + "\n"
              + "Lacks fertilizer: " + lacksFertilizer + "\n";

        System.out.print(s);
    }

    public CropName getName() {
        return name;
    }
    public void setName(CropName name) {
        this.name = name;
    }
    public CropType getType() {
        return type;
    }
    public void setType(CropType type) {
        this.type = type;
    }
    public double getBaseSeedCost() {
        return baseSeedCost;
    }
    public void setBaseSeedCost(double baseSeedCost) {
        this.baseSeedCost = baseSeedCost;
    }
    public double getBaseSellPrice() {
        return baseSellPrice;
    }
    public void setBaseSellPrice(double baseSellPrice) {
        this.baseSellPrice = baseSellPrice;
    }
    public double getExpYield() {
        return expYield;
    }
    public void setExpYield(double expYield) {
        this.expYield = expYield;
    }
    public int getHarvestAge() {
        return harvestAge;
    }
    public void setHarvestAge(int harvestAge) {
        this.harvestAge = harvestAge;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean isHarvestable() {
        return harvestable;
    }
    public void setHarvestable(boolean harvestable) {
        this.harvestable = harvestable;
    }
    public boolean isWithered() {
        return withered;
    }
    public void setWithered(boolean withered) {
        this.withered = withered;
    }
    public int getMinYield() {
        return minYield;
    }
    public void setMinYield(int minYield) {
        this.minYield = minYield;
    }
    public int getMaxYield() {
        return maxYield;
    }
    public void setMaxYield(int maxYield) {
        this.maxYield = maxYield;
    }
    public boolean isOverripe() {
        return overripe;
    }
    public void setOverripe(boolean overripe) {
        this.overripe = overripe;
    }
    public boolean isLacksWater() {
        return lacksWater;
    }
    public void setLacksWater(boolean lacksWater) {
        this.lacksWater = lacksWater;
    }
    public boolean isLacksFertilizer() {
        return lacksFertilizer;
    }
    public void setLacksFertilizer(boolean lacksFertilizer) {
        this.lacksFertilizer = lacksFertilizer;
    }
    public int getCurrentWater() {
        return currentWater;
    }
    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }
    public int getWaterNeeded() {
        return waterNeeded;
    }
    public void setWaterNeeded(int waterNeeded) {
        this.waterNeeded = waterNeeded;
    }
    public int getWaterLimit() {
        return waterLimit;
    }
    public void setWaterLimit(int waterLimit) {
        this.waterLimit = waterLimit;
    }
    public int getCurrentFertilizer() {
        return currentFertilizer;
    }
    public void setCurrentFertilizer(int currentFertilizer) {
        this.currentFertilizer = currentFertilizer;
    }
    public int getFertilizerNeeded() {
        return fertilizerNeeded;
    }
    public void setFertilizerNeeded(int fertilizerNeeded) {
        this.fertilizerNeeded = fertilizerNeeded;
    }
    public int getFertilizerLimit() {
        return fertilizerLimit;
    }
    public void setFertilizerLimit(int fertilizerLimit) {
        this.fertilizerLimit = fertilizerLimit;
    }
    public double getPremiumRate() {
        return premiumRate;
    }
    public void setPremiumRate(double premiumRate) {
        this.premiumRate = premiumRate;
    }
}
