package game.farm;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public enum FarmerType {
    DEFAULT("Default Farmer", 0.0, 0, 0.0, 0.0, 0, 0),
    REGISTERED("Registered Farmer", 200.0, 5, 1.0, 1.0, 0, 0),
    DISTINGUISHED("Distinguished Farmer", 300.0, 10, 2.0, 2.0, 1, 0),
    LEGENDARY("Legendary Farmer", 400.0, 15, 4.0, 3.0, 2, 1);


    private String stringName;
    private double regFee;
    private int levelReq;
    private double bonusEarnings;
    private double seedCostReduction;
    private int waterLimitIncrease;
    private int fertilizerLimitIncrease;

    private final static Map<String, FarmerType> stringNameToEnum;

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

    static {
        Map<String, FarmerType> map = new HashMap<String, FarmerType>();
        for (FarmerType f : FarmerType.values()) {
            map.put(f.getStringName().toLowerCase(), f);
        }
        stringNameToEnum = Collections.unmodifiableMap(map);
    }

    public static FarmerType getFromStringName(String name) {
        return stringNameToEnum.get(name.toLowerCase());
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

    @Override
    public String toString() {
        return stringName;
    }    
}
