package game.crop;

public class HarvestCropReport {
    private boolean success = true;
    private String message = "Harvest successful!";
    private int productsProduced;
    private int profit; // money gained from harvest
    private double expGained;


    public HarvestCropReport() {
    }
    
    public HarvestCropReport(int productsProduced, double expGained) {
        this.productsProduced = productsProduced;
        this.expGained = expGained;
    }

    public HarvestCropReport(int productsProduced, int profit, double expGained) {
        this.productsProduced = productsProduced;
        this.profit = profit;
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
    public int getProductsProduced() {
        return productsProduced;
    }
    public void setProductsProduced(int productsProduced) {
        this.productsProduced = productsProduced;
    }
    public int getProfit() {
        return profit;
    }
    public void setProfit(int profit) {
        this.profit = profit;
    }
    public double getExpGained() {
        return expGained;
    }
    public void setExpGained(double expGained) {
        this.expGained = expGained;
    }
}