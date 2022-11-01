package game.farm;

public class Farmer {
    private FarmerType farmerType;
    private double bonusEarnings;
    private double seedCostReduction;
    private int waterLimitIncrease;
    private int fertilizerLimitIncrease;

    
    public Farmer(FarmerType farmerType) {
        this.farmerType = farmerType;
        this.bonusEarnings = farmerType.getBonusEarnings();
        this.seedCostReduction = farmerType.getSeedCostReduction();
        this.waterLimitIncrease = farmerType.getWaterLimitIncrease();
        this.fertilizerLimitIncrease = farmerType.getFertilizerLimitIncrease();
    }


    public FarmerType getFarmerType() {
        return farmerType;
    }

    public void setFarmerType(FarmerType farmerType) {
        this.farmerType = farmerType;
    }

    public double getBonusEarnings() {
        return bonusEarnings;
    }

    public void setBonusEarnings(double bonusEarnings) {
        this.bonusEarnings = bonusEarnings;
    }

    public double getSeedCostReduction() {
        return seedCostReduction;
    }

    public void setSeedCostReduction(double seedCostReduction) {
        this.seedCostReduction = seedCostReduction;
    }

    public int getWaterLimitIncrease() {
        return waterLimitIncrease;
    }

    public void setWaterLimitIncrease(int waterLimitIncrease) {
        this.waterLimitIncrease = waterLimitIncrease;
    }

    public int getFertilizerLimitIncrease() {
        return fertilizerLimitIncrease;
    }

    public void setFertilizerLimitIncrease(int fertilizerLimitIncrease) {
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
    }
}
