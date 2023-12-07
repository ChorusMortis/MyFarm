/**
 * Records statistics about the player.
 */
public class Stats {
    public static final String DEFAULT_RANK_NAME = "Default Farmer";
    public static final String REGISTERED_RANK_NAME = "Registered Farmer";
    public static final String DISTINGUISHED_RANK_NAME = "Distinguished Farmer";
    public static final String LEGENDARY_RANK_NAME = "Legendary Farmer";

    // player basic stats
    private String rank;
    private double objectCoins;
    private int level;
    private double experience;

    // player farming stats
    private double bonusEarnings;
    private double seedCostReduction; // this number is deducted so this must be non-negative
    private int waterLimitIncrease;
    private int fertilizerLimitIncrease;

    // player's rank stats
    private int rankTier; // determines which ranks are "better" or "higher"
    private double rankCost; // how much it is to buy this rank
    private int rankRequiredLevel; // what level is required to buy this rank

    /**
     * Creates a new group of statistics.
     */
    public Stats() {
    }

    /**
     * Creates a new group of statistics and initializes them using the given
     * data.
     * @param rank                      Rank of the player.
     * @param objectCoins               The amount of money the player has.
     * @param level                     What level the player is.
     * @param experience                Amount of experience the player has.
     * @param bonusEarnings             Money added per crop sold.
     * @param seedCostReduction         Money deducted per crop bought.
     * @param waterLimitIncrease        Amount to increase the water limit by.
     * @param fertilizerLimitIncrease   Amount to increase the fertilizer
     *                                  limit by.
     * @param rankTier                  Determines which ranks are better.
     * @param rankCost                  Money required to buy the rank.
     * @param rankRequiredLevel         Level required to buy the rank.
     */
    public Stats(String rank, double objectCoins, int level, double experience, double bonusEarnings,
            double seedCostReduction, int waterLimitIncrease, int fertilizerLimitIncrease, int rankTier,
            double rankCost, int rankRequiredLevel) {
        this.rank = rank;
        this.objectCoins = objectCoins;
        this.level = level;
        this.experience = experience;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterLimitIncrease = waterLimitIncrease;
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
        this.rankTier = rankTier;
        this.rankCost = rankCost;
        this.rankRequiredLevel = rankRequiredLevel;
    }

    /**
     * Creates a new group of statistics meant for a new farmer type/rank.
     * @param rank                      Rank of the player.
     * @param bonusEarnings             Money added per crop sold.
     * @param seedCostReduction         Money deducted per crop bought.
     * @param waterLimitIncrease        Amount to increase the water limit by.
     * @param fertilizerLimitIncrease   Amount to increase the fertilizer
     *                                  limit by.
     * @param rankTier                  Determines which ranks are better.
     * @param rankCost                  Money required to buy the rank.
     * @param rankRequiredLevel         Level required to buy the rank.
     */
    public Stats(String rank, double bonusEarnings, double seedCostReduction, int waterLimitIncrease,
            int fertilizerLimitIncrease, int rankTier, double rankCost, int rankRequiredLevel) {
        this.rank = rank;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterLimitIncrease = waterLimitIncrease;
        this.fertilizerLimitIncrease = fertilizerLimitIncrease;
        this.rankTier = rankTier;
        this.rankCost = rankCost;
        this.rankRequiredLevel = rankRequiredLevel;
    }

    /**
     * Creates an instance of the default farmer type/rank and initializes
     * it with the stats of the default farmer.
     * @return   A Stats instance with the stats of the default farmer.
     */
    public static Stats createDefaultRank() {
        return new Stats(DEFAULT_RANK_NAME, 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Creates an instance of the registered farmer type/rank and initializes
     * it with the stats of the registered farmer.
     * @return   A Stats instance with the stats of the registered farmer.
     */
    public static Stats createRegisteredRank() {
        return new Stats(REGISTERED_RANK_NAME, 1, 1, 0, 0, 1, 200, 5);
    }

    /**
     * Creates an instance of the distinguished farmer type/rank and
     * initializes it with the stats of the distinguished farmer.
     * @return   A Stats instance with the stats of the distinguished farmer.
     */
    public static Stats createDistinguishedRank() {
        return new Stats(DISTINGUISHED_RANK_NAME, 2, 2, 1, 0, 2, 300, 10);
    }

    /**
     * Creates an instance of the legendary farmer type/rank and initializes
     * it with the stats of the legendary farmer.
     * @return   A Stats instance with the stats of the legendary farmer.
     */
    public static Stats createLegendaryRank() {
        return new Stats(LEGENDARY_RANK_NAME, 4, 3, 2, 1, 3, 400, 15);
    }

    /**
     * Sets the current Stats instance's farming and rank stats using the
     * given Stats instance.
     * @param newStats   The new farmer type/rank of the player.
     * @return   The updated Stats instance.
     */
    public Stats updateRank(Stats newStats) {
        this.setRank(newStats.getRank());
        this.setFarmingStats(newStats);
        this.setRankStats(newStats);
        return this;
    }

    /**
     * Sets the farming stats of the current Stats instance using the given
     * Stats instance.
     * @param newStats   The Stats instance containing the new farming stats.
     * @return   The updated Stats instance.
     */
    public Stats setFarmingStats(Stats newStats) {
        this.setBonusEarnings(newStats.getBonusEarnings());
        this.setSeedCostReduction(newStats.getSeedCostReduction());
        this.setWaterLimitIncrease(newStats.getWaterLimitIncrease());
        this.setFertilizerLimitIncrease(newStats.getFertilizerLimitIncrease());
        return this;
    }

    /**
     * Sets the rank stats of the current Stats instance using the given Stats
     * instance.
     * @param newStats   The Stats instance containing the new rank stats.
     * @return   The updated Stats instance.
     */
    public Stats setRankStats(Stats newStats) {
        this.setRankTier(newStats.getRankTier());
        this.setRankCost(newStats.getRankCost());
        this.setRankRequiredLevel(newStats.getRankRequiredLevel());
        return this;
    }

    /**
     * Adds money to the player's money count.
     * @param amount   The amount of money to be added.
     * @return   The new money count of the player.
     */
    public double addMoney(double amount) {
        return objectCoins += amount;
    }

    /**
     * Deducts money from the player's money count.
     * @param amount   The amount of money to be deducted.
     * @return   The new money count of the player.
     */
    public double deductMoney(double amount) {
        return objectCoins -= amount;
    }

    /**
     * Adds experience to the player and updates their level.
     * @param amount   The amount of experience to be added.
     * @return The new amount of experience the player has.
     */
    public double addExperience(double amount) {
        experience += amount;
        updateLevel();
        return experience;
    }

    /**
     * Updates the level of the player based on their experience.
     * @return The updated level of the player.
     */
    public int updateLevel() {
        return level = (int)(experience / 100);
    }

    /**
     * Returns a multiline string of the player's basic stats, such as their
     * current farmer type/rank, objectcoins, level, and experience.
     * @return   A multiline string of the player's basic stats.
     */
    public String getBasicStats() {
        var s = "Farmer Type: " + rank + "\n"
              + "Objectcoins: " + objectCoins + "\n"
              + "Level: " + level + "\n"
              + "Experience: " + experience + "\n";
        
        return s;
    }

    /**
     * Returns a multiline string of the player's farming stats, such as their
     * current bonus earnings, seed cost reduction, and water and fertilizer
     * limit increases.
     * @return   A multiline string of the player's farming stats.
     */
    public String getFarmingStats() {
        var s = "Bonus Earnings: " + bonusEarnings + "\n"
              + "Seed Cost Reduction: " + seedCostReduction + "\n"
              + "Water Limit Increase: " + waterLimitIncrease + "\n"
              + "Fertilizer Limit Increase: " + fertilizerLimitIncrease + "\n";
        
        return s;
    }

    /**
     * Returns a multiline string of the player's rank stats, such as their
     * current rank tier, rank cost, and required level for the rank.
     * @return   A multiline string of the player's rank stats.
     */
    public String getRankStats() {
        var s = "Rank Tier: " + rankTier + "\n"
              + "Rank Cost: " + rankCost + "\n"
              + "Required Level for Rank: " + rankRequiredLevel + "\n";
        
        return s;
    }

    /**
     * Returns a multiline string of the player's basic and farming stats.
     * @return   A multiline string of the player's basic and farming stats.
     * @see #getBasicStats()
     * @see #getFarmingStats()
     */
    public String getBasicAndFarmingStats() {
        var s = getBasicStats()
              + getFarmingStats();
        
        return s;
    }

    /**
     * Returns a multiline string of the player's farming and rank stats.
     * @return   A multiline string of the player's farming and rnak stats.
     * @see #getFarmingStats()
     * @see #getRankStats()
     */
    public String getFarmingAndRankStats() {
        var s = getFarmingStats()
              + getRankStats();

        return s;
    }
    
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public double getObjectCoins() {
        return objectCoins;
    }
    public void setObjectCoins(double objectCoins) {
        this.objectCoins = objectCoins;
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
    public int getRankTier() {
        return rankTier;
    }
    public void setRankTier(int rankTier) {
        this.rankTier = rankTier;
    }
    public double getRankCost() {
        return rankCost;
    }
    public void setRankCost(double rankCost) {
        this.rankCost = rankCost;
    }
    public int getRankRequiredLevel() {
        return rankRequiredLevel;
    }
    public void setRankRequiredLevel(int rankRequiredLevel) {
        this.rankRequiredLevel = rankRequiredLevel;
    }
}
