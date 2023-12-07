/**
 * Contains feedback for an action or option.
 */
public class ActionReport {
    protected boolean success;
    protected String message;
    protected double moneyCost;

    /**
     * Creates an action report.
     */
    public ActionReport() {
    }

    /**
     * Creates an action report and initializes its success state, feedback
     * message, and the money cost.
     * @param success     Whether the action was a success or not.
     * @param message     Feedback message for performing the action.
     * @param moneyCost   Money it costed to do the action.
     */
    public ActionReport(boolean success, String message, double moneyCost) {
        this.success = success;
        this.message = message;
        this.moneyCost = moneyCost;
    }
    
    /**
     * Returns a multiline string of the report's details, such as its
     * success state, feedback message, and money cost.
     * @return   A multiline string containing the report's details.
     */
	public String getDetails() {
        var s = "Success: " + success + "\n"
              + "Message: " + message + "\n"
              + "Money Deducted: " + moneyCost + "\n";
        
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
    public double getMoneyCost() {
        return moneyCost;
    }
    public void setMoneyCost(double moneyCost) {
        this.moneyCost = moneyCost;
    }
}
