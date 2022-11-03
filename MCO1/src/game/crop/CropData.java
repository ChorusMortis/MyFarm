package game.crop;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Stores data for each crop.
 */
public enum CropData {
    /**
     * Data for the turnip root crop. Not to be confused with the flower.
     */
    TURNIP(CropName.TURNIP, CropType.ROOT_CROP, 2, 1, 2, 0, 1, 1, 2, 5, 6, 5),

    /**
     * Data for the carrot root crop.
     */
    CARROT(CropName.CARROT, CropType.ROOT_CROP, 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5),

    /**
     * Data for the potato root crop.
     */
    POTATO(CropName.POTATO, CropType.ROOT_CROP, 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5),

    /**
     * Data for the rose flower.
     */
    ROSE(CropName.ROSE, CropType.FLOWER, 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5),

    /**
     * Data for the turnips flower. Not to be confused with the root crop.
     */
    TURNIPS(CropName.TURNIPS, CropType.FLOWER, 2, 2, 3, 0, 1, 1, 1, 10, 9, 5),

    /**
     * Data for the sunflower flower.
     */
    SUNFLOWER(CropName.SUNFLOWER, CropType.FLOWER, 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5),

    /**
     * Data for the mango fruit tree.
     */
    MANGO(CropName.MANGO, CropType.FRUIT_TREE, 10, 7, 7, 4, 4, 5, 15, 100, 8, 25),

    /**
     * Data for the apple fruit tree.
     */
    APPLE(CropName.APPLE, CropType.FRUIT_TREE, 10, 7, 7, 5, 5, 10, 15, 200, 5, 25);

    private static final Map<CropName, CropData> CropNameToEnum;
    private static final double lowestBaseSeedCost;
    
    private CropName name;
    private CropType type;
    private int harvestAge;
    private int neededWater;
    private int waterLimit;
    private int neededFertilizer;
    private int fertilizerLimit;
    private int minYield;
    private int maxYield;
    private double baseSeedCost;
    private double baseSellprice;
    private double expYield;

    static {
        Map<CropName, CropData> map = new HashMap<CropName, CropData>();
        for (CropData c : CropData.values()) {
            map.put(c.getName(), c);
        }
        CropNameToEnum = Collections.unmodifiableMap(map);
    }

    static {
        double tempLowestCost = CropData.values()[0].getBaseSeedCost();
        for (CropData c : CropData.values()) {
            double cost = c.getBaseSeedCost();
            if (cost < tempLowestCost) {
                tempLowestCost = cost;
            }
        }
        lowestBaseSeedCost = tempLowestCost;
    }

    /**
     * Creates data for a crop.
     * @param name               Name of the crop.
     * @param type               Type of the crop.
     * @param harvestAge         Days before a crop can become harvestable.
     * @param neededWater        Required water to become harvestable.
     * @param waterLimit         Upper bound for the water count.
     * @param neededFertilizer   Required fertilizer to become harvestable.
     * @param fertilizerLimit    Upper bound for the fertilizer count.
     * @param minYield           Lower bound for products produced.
     * @param maxYield           Upper bound for products produced.
     * @param baseSeedCost       Base buy cost when purchasing.
     * @param baseSellprice      Base sell price for each product sold.
     * @param expYield           Experience given when harvested.
     */
    private CropData(CropName name, CropType type, int harvestAge, int neededWater, int waterLimit,
            int neededFertilizer, int fertilizerLimit, int minYield, int maxYield, double baseSeedCost,
            double baseSellprice, double expYield) {
        this.name = name;
        this.type = type;
        this.harvestAge = harvestAge;
        this.neededWater = neededWater;
        this.waterLimit = waterLimit;
        this.neededFertilizer = neededFertilizer;
        this.fertilizerLimit = fertilizerLimit;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.baseSeedCost = baseSeedCost;
        this.baseSellprice = baseSellprice;
        this.expYield = expYield;
    }

    /**
     * Returns the crop data enum associated to the crop's name.
     * @param name   The name of the crop.
     * @return
     *    A CropData enum   if the crop name is mapped to a CropData enum.
     *    null              otherwise.
     */
    public static CropData getFromCropName(CropName name) {
        return CropNameToEnum.get(name);
    }
    
    /**
     * Returns the cheapest crop's base seed cost. Does not factor in the
     * player's seed cost reduction.
     * @return   The cheapest crop's base seed cost.
     */
    public static double getLowestBaseSeedCost() {
        return lowestBaseSeedCost;
    }

    public CropName getName() {
        return name;
    }
    public CropType getType() {
        return type;
    }
    public int getHarvestAge() {
        return harvestAge;
    }
    public int getNeededWater() {
        return neededWater;
    }
    public int getWaterLimit() {
        return waterLimit;
    }
    public int getNeededFertilizer() {
        return neededFertilizer;
    }
    public int getFertilizerLimit() {
        return fertilizerLimit;
    }
    public int getMinYield() {
        return minYield;
    }
    public int getMaxYield() {
        return maxYield;
    }
    public double getBaseSeedCost() {
        return baseSeedCost;
    }
    public double getBaseSellPrice() {
        return baseSellprice;
    }
    public double getExpYield() {
        return expYield;
    }

    /**
     * Gets the premium rate of a crop, depending on its type.
     * Flowers generate 10% more money than other crops.
     * @return   A crop's premium rate.
     */
    public double getPremiumRate() {
        double rate = 1.0;
        // flowers generate 10% more money because "they are pretty"
        if (type == CropType.FLOWER) {
            rate = 1.1;
        }
        return rate;
    }
}
