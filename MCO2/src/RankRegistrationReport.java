/**
 * Contains feedback for farmer type/rank registration.
 */
public class RankRegistrationReport extends ActionReport {
    private Stats newRank;

    /**
     * Creates a rank registration report.
     */
    public RankRegistrationReport() {
    }

    /**
     * Creates a rank registration report and initializes its success state,
     * feedback message, money cost, and the new farmer type/rank that the
     * player registered for.
     * @param success     Whether the action was a success or not.
     * @param message     Feedback message for performing the action.
     * @param moneyCost   Money it costed to do the action.
     * @param newRank     The new farmer type/rank that the player registered
     *                    for.
     */
    public RankRegistrationReport(boolean success, String message, double moneyCost, Stats newRank) {
        super(success, message, moneyCost);
        this.newRank = newRank;
    }

    @Override
    public String getDetails() {
        var s = super.getDetails();

        if (success) {
            s += "\n"
               + "New Rank:\n\n"
               + "Farmer Type: " + newRank.getRank() + "\n"
               + newRank.getFarmingStats() + "\n";   
        }

        return s;
    }
    
    public Stats getNewRank() {
        return newRank;
    }
    public void setNewRank(Stats newRank) {
        this.newRank = newRank;
    }
}
