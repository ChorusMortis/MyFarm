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
        if (currentWater > waterLimit) {
            currentWater = waterLimit;
        }
    }

    public void addFertilizer(int amount) {
        currentFertilizer += amount;
        if (currentFertilizer > fertilizerLimit) {
            currentFertilizer = fertilizerLimit;
        }
    }

    // Convenience method for adding age
    public void grow() {
        addAge(1);
    }

    // Convenience method for adding water
    public void water() {
        addWater(1);
    }

    // Convenience method for adding fertilizer
    public void fertilize() {
        addFertilizer(1);
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
        int productsProduced = getRandomYield();
        return new HarvestCropReport(productsProduced, 0, expYield);
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

    public int getBaseSeedCost() {
        return baseSeedCost;
    }

    public void setBaseSeedCost(int baseSeedCost) {
        this.baseSeedCost = baseSeedCost;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public void setBaseSellPrice(int baseSellPrice) {
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