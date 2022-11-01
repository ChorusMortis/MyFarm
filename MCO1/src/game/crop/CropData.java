package game.crop;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public enum CropData {
    TURNIP(CropName.TURNIP, CropType.ROOT_CROP, 2, 1, 2, 0, 1, 1, 2, 5, 6, 5),
    CARROT(CropName.CARROT, CropType.ROOT_CROP, 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5),
    POTATO(CropName.POTATO, CropType.ROOT_CROP, 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5),
    ROSE(CropName.ROSE, CropType.FLOWER, 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5),
    TURNIPS(CropName.TURNIPS, CropType.FLOWER, 2, 2, 3, 0, 1, 1, 1, 10, 9, 5),
    SUNFLOWER(CropName.SUNFLOWER, CropType.FLOWER, 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5),
    MANGO(CropName.MANGO, CropType.FRUIT_TREE, 10, 7, 7, 4, 4, 5, 15, 100, 8, 25),
    APPLE(CropName.APPLE, CropType.FRUIT_TREE, 10, 7, 7, 5, 5, 10, 15, 200, 5, 25);

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

    private static final Map<CropName, CropData> ENUM_MAP;

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

    static {
        Map<CropName, CropData> map = new HashMap<CropName, CropData>();
        for (CropData c : CropData.values()) {
            map.put(c.getName(), c);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CropData getFromCropName(CropName name) {
        return ENUM_MAP.get(name);
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
    public double getPremiumRate() {
        double rate = 1.0;
        if (type == CropType.FLOWER) {
            rate = 1.1;
        }
        return rate;
    }
}
