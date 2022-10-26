package game.tile;

public class TileActionReport {
    private boolean success;
    private String message;


    public TileActionReport() {
    }

    public TileActionReport(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    @Override
	public String toString() {
		return "Report [Success: " + success + ", Reason: " + message + "]";
	}
}
