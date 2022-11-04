package game.player;

import game.farm.FarmerType;

/**
 * Contains feedback when registering for a farmer type/rank.
 */
public class RankRegistrationReport {
    public static final String REGISTER_SUCCESS = "Registration successful!";
    public static final String REGISTER_NO_MONEY = "You have insufficient money!";
    public static final String REGISTER_LOW_LEVEL = "You have not reached the required level for the rank!";
    public static final String REGISTER_IS_LOWER_RANK = "You have already registered for a higher rank!";
    public static final String REGISTER_IS_CURRENT_RANK = "You are currently registered for this rank!";
    
    private boolean success;
    private String message;
    private FarmerType newRank;
    private double newBonusEarnings;
    private double newSeedCostReduction;
    private int newWaterLimitIncrease;
    private int newFertilizerLimitIncrease;
    private double cost;

    /**
     * Creates a registration report.
     */
    public RankRegistrationReport() {
    }
    
    /**
     * Initializes a registration report using set data, including if it was
     * a success, a message, the new rank registered for, stat/perk changes,
     * and the cost of registration.
     * @param success                      Whether the registration was
     *                                     successful or not.
     * @param message                      Feedback message.
     * @param newRank                      New rank of the player.
     * @param newBonusEarnings             New amount of money added per
     *                                     product sold.
     * @param newSeedCostReduction         New amount of money deducted per
     *                                     crop bought.
     * @param newWaterLimitIncrease        New water limit increase.
     * @param newFertilizerLimitIncrease   New fertilize limit increase.
     * @param cost                         Cost of the registration.
     */
    public RankRegistrationReport(boolean success, String message, FarmerType newRank, double newBonusEarnings,
            double newSeedCostReduction, int newWaterLimitIncrease, int newFertilizerLimitIncrease, double cost) {
        this.success = success;
        this.message = message;
        this.newRank = newRank;
        this.newBonusEarnings = newBonusEarnings;
        this.newSeedCostReduction = newSeedCostReduction;
        this.newWaterLimitIncrease = newWaterLimitIncrease;
        this.newFertilizerLimitIncrease = newFertilizerLimitIncrease;
        this.cost = cost;
    }

    /**
     * Returns the state of the report, including if the registration was a
     * success and the corresponding feedback.
     */
    @Override
    public String toString() {
        var s = "Success: " + success + "\n"
              + "Message: " + message + "\n";

        return s;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public FarmerType getNewRank() {
        return newRank;
    }
    public void setNewRank(FarmerType newRank) {
        this.newRank = newRank;
    }
    public double getNewBonusEarnings() {
        return newBonusEarnings;
    }
    public void setNewBonusEarnings(double newBonusEarnings) {
        this.newBonusEarnings = newBonusEarnings;
    }
    public double getNewSeedCostReduction() {
        return newSeedCostReduction;
    }
    public void setNewSeedCostReduction(double newSeedCostReduction) {
        this.newSeedCostReduction = newSeedCostReduction;
    }
    public int getNewWaterLimitIncrease() {
        return newWaterLimitIncrease;
    }
    public void setNewWaterLimitIncrease(int newWaterLimitIncrease) {
        this.newWaterLimitIncrease = newWaterLimitIncrease;
    }
    public int getNewFertilizerLimitIncrease() {
        return newFertilizerLimitIncrease;
    }
    public void setNewFertilizerLimitIncrease(int newFertilizerLimitIncrease) {
        this.newFertilizerLimitIncrease = newFertilizerLimitIncrease;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
}
