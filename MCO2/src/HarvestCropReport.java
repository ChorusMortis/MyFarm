/**
 * Contains feedback for a harvest.
 */
public class HarvestCropReport extends TileActionReport {
    public static String HARVEST_NO_CROP = "There is no crop to harvest!";
    public static String HARVEST_NOT_HARVESTABLE = "This crop is not harvestable!";
    public static String HARVEST_SUCCESS = "The crop was successfully harvested!";

    private int productsProduced;
    private double profit;

    /**
     * Creates a harvest crop report.
     */
    public HarvestCropReport() {
    }

    /**
     * Creates a harvest crop report and initializes its success state,
     * feedback message, money cost, products produced, and the money gained.
     * @param success            Whether the action was a success or not.
     * @param message            Feedback message for performing the action.
     * @param moneyCost          Money it costed to do the action.
     * @param expGained          Total experience gained.
     * @param productsProduced   Number of products produced.
     * @param profit             Money gained after doing the action.
     */
    public HarvestCropReport(boolean success, String message, double moneyCost, double expGained, int productsProduced, double profit) {
        super(success, message, moneyCost, expGained);
        this.productsProduced = productsProduced;
        this.profit = profit;
    }
    
    @Override
    public String getDetails() {
        var s = super.getDetails()
              + "Products Produced: " + productsProduced + "\n"
              + "Profit: " + profit + "\n";
        
        return s;
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
}
