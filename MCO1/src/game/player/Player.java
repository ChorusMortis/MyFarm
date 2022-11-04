package game.player;

import game.farm.*;

/**
 * Represents the player which holds the farmer type/rank they have, their
 * farm lot, money, level, experience, and other tracked statistics.
 */
public class Player {
    private Farmer farmer;
    private FarmLot farmLot;
    private double objectCoins;
    private Stats stats;
    private int level;
    private double experience;

    /**
     * Creates a player.
     */
    public Player() {
    }
    
    /**
     * Creates a player and initializes it using the given farmer type, money
     * count, level, and experience. The rest are initialized automatically.
     * @param farmer
     * @param objectCoins
     * @param level
     * @param experience
     * @see FarmerType
     */
    public Player(FarmerType farmer, double objectCoins, int level, double experience) {
        this.farmer = new Farmer(farmer);
        this.farmLot = new FarmLot();
        this.objectCoins = objectCoins;
        this.stats = new Stats();
        this.level = level;
        this.experience = experience;
    }

    /**
     * Creates a player and initializes it using the given farmer type, farm
     * lot, money count, stats, level, and experience.
     * @param farmer        The type/rank of farmer the player has.
     * @param farmLot       The farm lot (collection of tles) the player has.
     * @param objectCoins   Initial money count.
     * @param stats         A collection of tracked statistics.
     * @param level         Initial starting level.
     * @param experience    Initial experience.
     * @see FarmerType
     * @see FarmLot
     * @see Stats
     */
    public Player(Farmer farmer, FarmLot farmLot, double objectCoins, Stats stats, int level, double experience) {
        this.farmer = farmer;
        this.farmLot = farmLot;
        this.objectCoins = objectCoins;
        this.stats = stats;
        this.level = level;
        this.experience = experience;
    }

    /**
     * Registers the player for a given type/rank. The stats of the crops
     * in the field are externally updated when the player
     * {@link app.Application#openRegisterMenu() registers for a rank}.
     * Newly bought crops' stats are updated when the player
     * {@link app.Application#openBuyCropMenu() buys a crop}.
     * @param farmerType   The type/rank to be registered as.
     * @return A report containing details about the registration.
     * @see app.Application Application
     * @see RankRegistrationReport
     */
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
            // the stats of the crops in the field are updated externally
            report.setSuccess(true);
            report.setMessage(RankRegistrationReport.REGISTER_SUCCESS);

            // update player's perks
            report.setNewRank(farmerType);
            report.setNewBonusEarnings(farmerType.getBonusEarnings());
            report.setNewSeedCostReduction(farmerType.getSeedCostReduction());
            report.setNewWaterLimitIncrease(farmerType.getWaterLimitIncrease());
            report.setNewFertilizerLimitIncrease(farmerType.getFertilizerLimitIncrease());
            report.setCost(rankRegFee);
        }

        return report;
    }

    /**
     * Adds money to the player's money count.
     * @param amount   The amount of money to be added.
     * @return The new money count of the player.
     */
    public double addMoney(double amount) {
        return objectCoins += amount;
    }

    /**
     * Deducts money from the player's money count.
     * @param amount   The amount of money to be deducted.
     * @return The new money count of the player.
     */
    public double deductMoney(double amount) {
        return objectCoins -= amount;
    }

    /**
     * Updates the level of the player based on their experience.
     * @return The updated level of the player.
     */
    public int updateLevel() {
        return level = (int)(experience / 100);
    }
    
    /**
     * Adds experience to the player and updates their level.
     * @param amount   The amount of experience to be added.
     * @return The new amount of experience the player has.
     */
    public double addXP(double amount) {
        experience += amount;
        updateLevel();
        return experience;
    }
    
    /**
     * Prints the state of the player, including their current money count,
     * level, and experience.
     */
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
