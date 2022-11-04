package game.farm;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Holds set data for each farmer type/rank.
 */
public enum FarmerType {
    /**
     * Data for the default farmer rank.
     */
    DEFAULT("Default Farmer", 0.0, 0, 0.0, 0.0, 0, 0),

    /**
     * Data for the registered farmer rank.
     */
    REGISTERED("Registered Farmer", 200.0, 5, 1.0, 1.0, 0, 0),

    /**
     * Data for the distinguished farmer rank.
     */
    DISTINGUISHED("Distinguished Farmer", 300.0, 10, 2.0, 2.0, 1, 0),

    /**
     * Data for the legendary farmer rank.
     */
    LEGENDARY("Legendary Farmer", 400.0, 15, 4.0, 3.0, 2, 1);

    private final static Map<String, FarmerType> stringNameToEnum;

    private String stringName;
    private double regFee;
    private int levelReq;
    private double bonusEarnings;
    private double seedCostReduction;
    private int waterLimitIncrease;
    private int fertilizerLimitIncrease;

    static {
        Map<String, FarmerType> map = new HashMap<String, FarmerType>();
        for (FarmerType f : FarmerType.values()) {
            map.put(f.getStringName().toLowerCase(), f);
        }
        stringNameToEnum = Collections.unmodifiableMap(map);
    }

    /**
     * Creates a collection of set data for a farmer type/rank.
     * @param stringName                The string name of the farmer type.
     * @param regFee                    Money requirement for registration.
     * @param levelReq                  Level requirement for registration.
     * @param bonusEarnings             Money added per product sold.
     * @param seedCostReduction         Money deducted when buying the crop.
     * @param waterLimitIncrease        Amount to increase the water limit of
     *                                  the crop by.
     * @param fertilizerLimitIncrease   Amount to increase the fertilizer
     *                                  limit of the crop by.
     */
    private FarmerType(String stringName, double regFee, int levelReq, double bonusEarnings, double seedCostReduction,
            int waterLimitIncrease, int fertilizerLimitIncrease) {
        this.stringName = stringName;
        this.regFee = regFee;
        this.levelReq = levelReq;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterLimitIncrease = waterLimitIncrease;
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
    }

    /**
     * Returns the FarmerType enum associated to the type's string name.
     * @param name   The string name of the farmer type.
     * @return
     *    A FarmerType enum   if the string name is mapped to a FarmerType
     *                        enum.
     *    null                otherwise.
     */
    public static FarmerType getFromStringName(String name) {
        return stringNameToEnum.get(name.toLowerCase());
    }

    /**
     * Returns the string name of the farmer type enum.
     */
    @Override
    public String toString() {
        return stringName;
    }
    
    public String getStringName() {
        return stringName;
    }
    public double getRegFee() {
        return regFee;
    }
    public int getLevelReq() {
        return levelReq;
    }
    public double getBonusEarnings() {
        return bonusEarnings;
    }
    public double getSeedCostReduction() {
        return seedCostReduction;
    }
    public int getWaterLimitIncrease() {
        return waterLimitIncrease;
    }
    public int getFertilizerLimitIncrease() {
        return fertilizerLimitIncrease;
    }
}
