package game.farmer;

public enum FarmerType {
    DEFAULT("Default Farmer", 0, 0, 0, 0, 0, 0),
    REGISTERED("Registered Farmer", 200, 5, 1, 1, 0, 0),
    DISTINGUISHED("Distinguished Farmer", 300, 10, 2, 2, 1, 0),
    LEGENDARY("Legendary Farmer", 400, 15, 4, 3, 2, 1);


    String stringName;
    int regFee;
    int levelReq;
    int bonusEarnings;
    int seedCostReduction;
    int waterLimitIncrease;
    int fertilizerLimitIncrease;


    private FarmerType(String stringName, int regFee, int levelReq, int bonusEarnings, int seedCostReduction,
            int waterLimitIncrease, int fertilizerLimitIncrease) {
        this.stringName = stringName;
        this.regFee = regFee;
        this.levelReq = levelReq;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterLimitIncrease = waterLimitIncrease;
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
    }

    
    public String getStringName() {
        return stringName;
    }

    public int getRegFee() {
        return regFee;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getBonusEarnings() {
        return bonusEarnings;
    }

    public int getSeedCostReduction() {
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
