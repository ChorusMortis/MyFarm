package game.player;

import game.farm.*;

public class Player {
    private Farmer farmer;
    private FarmLot farmLot;
    private double objectCoins;
    private Stats stats;
    private int level;
    private double experience;

    public Player() {
    }
    
    public Player(FarmerType farmer, double objectCoins, int level, double experience) {
        this.farmer = new Farmer(farmer);
        this.farmLot = new FarmLot();
        this.objectCoins = objectCoins;
        this.stats = new Stats();
        this.level = level;
        this.experience = experience;
    }

    public Player(Farmer farmer, FarmLot farmLot, double objectCoins, Stats stats, int level, double experience) {
        this.farmer = farmer;
        this.farmLot = farmLot;
        this.objectCoins = objectCoins;
        this.stats = stats;
        this.level = level;
        this.experience = experience;
    }

    public RankRegistrationReport registerRank(FarmerType farmerType) {
        RankRegistrationReport report = new RankRegistrationReport();

        double rankRegFee = farmerType.getRegFee();
        int curLevelReq = this.farmer.getFarmerType().getLevelReq();
        int rankLevelReq = farmerType.getLevelReq();

        if (rankLevelReq < curLevelReq) {
            report.setMessage(RankRegistrationReport.REGISTER_IS_LOWER_RANK);
        } else if (rankLevelReq == curLevelReq) {
            report.setMessage(RankRegistrationReport.REGISTER_IS_CURRENT_RANK);
        } else if (level < rankLevelReq) {
            report.setMessage(RankRegistrationReport.REGISTER_LOW_LEVEL);
        } else if (objectCoins < rankRegFee) {
            report.setMessage(RankRegistrationReport.REGISTER_NO_MONEY);
        } else {
            // the stats of the crops in the field are updated externally (see Application class)
            report.setSuccess(true);
            report.setMessage(RankRegistrationReport.REGISTER_SUCCESS);
            report.setNewRank(farmerType);
            report.setNewBonusEarnings(farmerType.getBonusEarnings());
            report.setNewSeedCostReduction(farmerType.getSeedCostReduction());
            report.setNewWaterLimitIncrease(farmerType.getWaterLimitIncrease());
            report.setNewFertilizerLimitIncrease(farmerType.getFertilizerLimitIncrease());
            report.setCost(rankRegFee);
        }

        return report;
    }

    public double addMoney(double amount) {
        return objectCoins += amount;
    }

    public double deductMoney(double amount) {
        return objectCoins -= amount;
    }

    public int updateLevel() {
        return level = (int)(experience / 100);
    }
    
    public double addXP(double amount) {
        experience += amount;
        updateLevel();
        return experience;
    }
    
    public void printState() {
        var s = "Objectcoins: " + objectCoins + "\n"
              + "Level: " + level + "\n"
              + "Experience: " + experience + "\n"
              + stats + "\n";
        
        System.out.print(s);
    }

    public Farmer getFarmer() {
        return farmer;
    }
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
    public FarmLot getFarmLot() {
        return farmLot;
    }
    public void setFarmLot(FarmLot farmLot) {
        this.farmLot = farmLot;
    }
    public double getObjectCoins() {
        return objectCoins;
    }
    public void setObjectCoins(double objectCoins) {
        this.objectCoins = objectCoins;
    }
    public Stats getStats() {
        return stats;
    }
    public void setStats(Stats stats) {
        this.stats = stats;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public double getExperience() {
        return experience;
    }
    public void setExperience(double experience) {
        this.experience = experience;
    }
}
