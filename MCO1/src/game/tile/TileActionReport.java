package game.tile;

/**
 * Contains feedback for a tile action/option.
 */
public class TileActionReport {
    private boolean success;
    private String message;
    private double expGained;

    /**
     * Creates a tile action report.
     */
    public TileActionReport() {
    }

    /**
     * Creates a tile action report and initializes its success state,
     * feedback message, and gained experience.
     * @param success     Whether the action was a success or not.
     * @param message     Feedback message for performing the action.
     * @param expGained   Experience gained for performing the action.
     */
    public TileActionReport(boolean success, String message, double expGained) {
        this.success = success;
        this.message = message;
        this.expGained = expGained;
    }

    /**
     * Returns a string of the report's details, including its success state
     * and feedback message.
     */
    @Override
	public String toString() {
        var s = "Report:\n"
              + "Success: " + success + "\n"
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
    public double getExpGained() {
        return expGained;
    }
    public void setExpGained(double expGained) {
        this.expGained = expGained;
    }
}
