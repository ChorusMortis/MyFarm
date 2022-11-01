package game.player;

import game.farm.FarmLot;
import game.farm.Farmer;
import game.farm.FarmerType;

// TODO: implement this class
public class Player {
    private Farmer farmer;
    private FarmLot farmLot;

    private double objectCoins;
    private Stats stats;
    private int level;
    private double experience;


    public double addMoney(double amount) {
        return objectCoins += amount;
    }

    public double deductMoney(double amount) {
        return objectCoins -= amount;
    }

    public double addXP(double amount) {
        return experience += amount;
    }

    public int updateLevel() {
        return level = (int)experience % 100;
    }

    public Player(FarmerType farmer) {
        this.farmer = new Farmer(farmer);
        this.farmLot = new FarmLot();
        this.objectCoins = 0.0;
        this.stats = new Stats();
        this.level = 0;
        this.experience = 0.0;
    }

    public Player(Farmer farmer, FarmLot farmLot, double objectCoins, Stats stats, int level, double experience) {
        this.farmer = farmer;
        this.farmLot = farmLot;
        this.objectCoins = objectCoins;
        this.stats = stats;
        this.level = level;
        this.experience = experience;
    }

    // TODO: insert setters and getters
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
