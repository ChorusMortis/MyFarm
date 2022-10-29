package game.tile;

public class TileActionReport {
    private boolean success;
    private String message;
    private double expGained;


    public TileActionReport() {
    }

    public TileActionReport(boolean success, String message, double expGained) {
        this.success = success;
        this.message = message;
        this.expGained = expGained;
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

    @Override
	public String toString() {
		return "Report [Success: " + success + ", Reason: " + message + "]";
	}
}
