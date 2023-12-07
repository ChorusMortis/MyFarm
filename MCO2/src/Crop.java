/**
 * A crop that can be planted and sold for profit.
 */
public abstract class Crop {
    // Crop description
    protected String name;
    protected String type;
    protected double baseSeedCost;
    protected double baseSellPrice;
    protected double expYield;

    // Harvesting
    protected int harvestAge;
    protected int age = 0;
    protected boolean harvestable;
    protected boolean withered;
    protected int minYield;
    protected int maxYield;

    // Withering reasons
    protected boolean overripe;
    protected boolean lacksWater;
    protected boolean lacksFertilizer;

    // Watering stats
    protected int currentWater;
    protected int waterNeeded;
    protected int waterLimit;

    // Fertilizer
    protected int currentFertilizer;
    protected int fertilizerNeeded;
    protected int fertilizerLimit;

    // Special mechanics
    protected double premiumRate = 1.0;
    
    /**
     * Creates a new crop.
     */
    public Crop() {
    }

    /**
     * Creates a new crop and initializes its data.
     * @param name               Name of the crop.
     * @param type               Type of the crop.
     * @param baseSeedCost       Base buy cost when purchasing.
     * @param baseSellPrice      Base sell price for each product sold.
     * @param expYield           Experience given per product when harvested.
     * @param harvestAge         Days before a crop can become harvestable.
     * @param minYield           Lower bound for products produced.
     * @param maxYield           Upper bound for products produced.
     * @param waterNeeded        Required water to become harvestable.
     * @param waterLimit         Upper bound for the water count.
     * @param fertilizerNeeded   Required fertilizer to become harvestable.
     * @param fertilizerLimit    Upper bound for the fertilizer count.
     * @param premiumRate        Premium rate for each piece sold.
     */
    public Crop(String name, String type, double baseSeedCost, double baseSellPrice, double expYield,
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
     * current water and fertilizer count.
     */
    public void nextDay() {
        grow(); // crop ages regardless of state

        if (withered || age < harvestAge) {
            return;
        }

        if (currentWater < waterNeeded) {
            lacksWater = true;
        }
        
        if (currentFertilizer < fertilizerNeeded) {
            lacksFertilizer = true;
        }
        
        if (age > harvestAge) {
            overripe = true;
        }

        if (lacksWater || lacksFertilizer || overripe) {
            harvestable = false;
            withered = true;
        } else {
            harvestable = true;
        }
    }

    /**
     * Calculates the total sell price for the harvested crop given the amount
     * of products produced and how much money is added per product sold.
     * @param productsProduced   The amount of products harvested.
     * @param bonusEarnings      Money added per product sold.
     * @return   Total selling price for the harvested crop.
     */
    public double calculateSellPrice(int productsProduced, double bonusEarnings) {
        double basePrice = productsProduced * (baseSellPrice + bonusEarnings);
        double waterBonus = basePrice * 0.2 * (currentWater - 1);
        double fertilizerBonus = basePrice * 0.5 * currentFertilizer;
        double finalPrice = (basePrice + waterBonus + fertilizerBonus) * premiumRate;
        return finalPrice;
    }
    
    /**
     * Randomly generates a number between the crop's minimum and maximum
     * product yield, both inclusive.
     * @return   The randomly generated yield.
     */
    public int getRandomYield() {
        return (int)Math.floor((Math.random() * (maxYield - minYield + 1)) + minYield);
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
     * Increases the crop's water and fertilizer limit by the given amounts.
     * @param waterLimitIncrease        Amount to increase the water limit by.
     * @param fertilizerLimitIncrease   Amount to increase the fertilizer
     *                                  limit by.
     */
    public void updateWaterAndFertilizerLimits(int waterLimitIncrease, int fertilizerLimitIncrease) {
        waterLimit += waterLimitIncrease;
        fertilizerLimit += fertilizerLimitIncrease;
    }

    /**
     * Returns a multiline string containing the crop's current attributes:
     * name, type, age, current water count, current fertilizer count, if it's
     * harvestable or withered, and reason for withering, if it is withered.
     * @return   A multiline string containing the crop's current details.
     */
    public String getDetails() {
        var s = "Crop Name: " + name + "\n"
              + "Crop Type: " + type + "\n"
              + "Age: " + age + " / " + harvestAge + "\n"
              + "Water: " + currentWater + " / " + waterNeeded + "(" + waterLimit + ")" + "\n"
              + "Fertilizer: " + currentFertilizer + " / " + fertilizerNeeded + "(" + fertilizerLimit + ")" + "\n"
              + "Is harvestable: " + harvestable + "\n"
              + "Is withered: " + withered + "\n"
              + "Is overripe: " + overripe + "\n"
              + "Lacks water: " + lacksWater + "\n"
              + "Lacks fertilizer: " + lacksFertilizer + "\n";

        return s;
    }

    /**
     * Returns a multiline string containing the crop's purchasing details:
     * name, type, base seed cost, base sell price, experience yield, harvest
     * time, product yield, water and fertilizer requirements, premium rate,
     * and other crop type-specific details.
     * @return   A multiline string containing the crop's base stats.
     */
    public String getBaseStats() {
        var s = "Crop Name: " + name + "\n"
              + "Crop Type: " + type + "\n"
              + "Base Seed Cost: " + baseSeedCost + "\n"
              + "Base Sell Price (per product sold): " + baseSellPrice + "\n"
              + "Experience Yield: " + expYield + "\n"
              + "Harvest Time (in days): " + harvestAge + "\n"
              + "Yield: " + minYield + "-" + maxYield + "\n"
              + "Water Requirements: " + waterNeeded + " (" + waterLimit + ")\n"
              + "Fertilizer Requirements: " + fertilizerNeeded + " (" + fertilizerLimit + ")\n"
              + "Premium Rate: " + premiumRate + "\n";
        
        if (type.equals("Fruit Tree")) {
            s += "This crop requires free adjacent spaces to plant.\n";
        }
        
        return s;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
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
