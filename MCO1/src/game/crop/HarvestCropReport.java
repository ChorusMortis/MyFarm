package game.crop;

/**
 * Contains feedback info for a harvest.
 */
public class HarvestCropReport {
    private boolean success = true;
    private String message = "Harvest successful!";
    private int productsProduced;
    private double profit; // money gained from harvest
    private double expGained;

    /**
     * Creates a harvest report.
     */
    public HarvestCropReport() {
    }

    /**
     * Creates a harvest report with a set amount of products produced and
     * experience gained.
     * @param productsProduced   The products produced upon harvest.
     * @param expGained          The experience gained upon harvest.
     */
    public HarvestCropReport(int productsProduced, double expGained) {
        this.productsProduced = productsProduced;
        this.expGained = expGained;
    }

    /**
     * Creates a harvest report with a set amount of products produced,
     * profit, and experience gained.
     * @param productsProduced   The products produced upon harvest.
     * @param profit             The profit when the crop(s) is sold.
     * @param expGained          The experience gained upon harvest.
     */
    public HarvestCropReport(int productsProduced, double profit, double expGained) {
        this.productsProduced = productsProduced;
        this.profit = profit;
        this.expGained = expGained;
    }

    /**
     * Returns the details of the report, including if it was a success,
     * feedback, the amount of products produced, profit, and experience
     * gained.
     */
    @Override
    public String toString() {
        var s = "Harvest Report:\n"
              + "Success: " + success + "\n"
              + "Message: " + message + "\n"
              + "Products Produced: " + productsProduced + "\n"
              + "Profit: " + profit + "\n"
              + "Experience Gained: " + expGained + "\n";

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
    public int getProductsProduced() {
        return productsProduced;
    }
    public void setProductsProduced(int productsProduced) {
        this.productsProduced = productsProduced;
    }
    public double getProfit() {
        return profit;
    }
    public void setProfit(double profit) {
        this.profit = profit;
    }
    public double getExpGained() {
        return expGained;
    }
    public void setExpGained(double expGained) {
        this.expGained = expGained;
    }
}
