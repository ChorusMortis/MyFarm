package game.farm;

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
