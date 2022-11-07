package game.tile;

import game.crop.*;

/**
 * Represents each tile in the farmer's farm lot that can grow a crop on.
 */
public class Tile {
    private Crop plantedCrop;
    private boolean plowed;
    
    /**
     * Creates a tile.
     */
    public Tile() {
    }
    
    /**
     * Creates a tile, initializes it with a crop, and sets its plowed state.
     * @param plantedCrop   The crop planted on the tile.
     * @param plowed        Whether the crop is plowed or not.
     */
    public Tile(Crop plantedCrop, boolean plowed) {
        this.plantedCrop = plantedCrop;
        this.plowed = plowed;
    }

    /**
     * Plows the tile and returns a report detailing if it was successful and
     * feedback about the action. Plowing fails if one of the following
     * conditions is met:
     *    1. The tile is already plowed.
     *    2. The tile already has a crop.
     * @return   Report about the action.
     * @see TileActionReportMessages
     */
    public TileActionReport plow() {
        TileActionReport report = new TileActionReport(false, null, 0);

        if (plowed) {
            report.setMessage(TileActionReportMessages.PLOW_IS_PLOWED.toString());
        } else if (plantedCrop != null) {
            report.setMessage(TileActionReportMessages.PLOW_HAS_CROP.toString());
        } else {
            plowed = true;
            report.setSuccess(true);
            report.setMessage(TileActionReportMessages.PLOW_SUCCESS.toString());
            report.setExpGained(TileActionData.PLOW.getExpYield());
        }

        return report;
    }
    
    /**
     * Plants the given crop and returns a report detailing if it was
     * successful and feedback about the action. Planting fails if one of the
     * following conditions is met:
     *    1. The player has insufficient money.
     *    2. The tile already has a planted crop.
     * @pre The tile is plowed.
     * @param crop                The crop to be planted.
     * @param playerMoney         Player's current amount of money.
     * @param seedCostReduction   Discount for the purchase.
     * @return   Report about the action.
     * @see TileActionReportMessages
     */
    public TileActionReport plant(Crop crop, double playerMoney, double seedCostReduction) {
        TileActionReport report = new TileActionReport(false, null, 0);

        if (playerMoney < crop.getBaseSeedCost() - seedCostReduction) {
            report.setMessage(TileActionReportMessages.PLANT_NO_MONEY.toString());
        } else if (plantedCrop != null) {
            report.setMessage(TileActionReportMessages.PLANT_HAS_CROP.toString());
        } else {
            plantedCrop = crop;
            report.setSuccess(true);
            report.setMessage(TileActionReportMessages.PLANT_SUCCESS.toString());
            report.setExpGained(TileActionData.PLANT.getExpYield());
        }

        return report;
    }

    /**
     * Harvests the crop, removes it from the tile, and returns a report
     * detailing if it was successful and feedback about the action.
     * Harvesting is always successful.
     * @pre plantedCrop != null and isHarvestable()
     * @param bonusEarnings   Added money per product sold.
     * @return   Report about the action.
     * @see Crop#harvest()
     * @see #calculateSellPrice(int, double)
     * @see TileActionReportMessages
     */
    public HarvestCropReport harvest(double bonusEarnings) {
        // products produced and exp yield are calculated in plantedCrop's harvest() method
        HarvestCropReport report = plantedCrop.harvest();
        double sellPrice = calculateSellPrice(report.getProductsProduced(), bonusEarnings);
        report.setProfit(sellPrice);

        plantedCrop = null;
        plowed = false;
        
        return report;
    }

    /**
     * Waters the crop and returns a report detailing if it was successful and
     * feedback about the action. Watering is always successful.
     * @pre plantedCrop != null and is !withered
     * @return   Report about the action.
     * @see TileActionReportMessages
     */
    public TileActionReport water() {
        plantedCrop.water();
        TileActionReport report = new TileActionReport(true, null, 0);
        report.setMessage(TileActionReportMessages.WATER_SUCCESS.toString());
        report.setExpGained(TileActionData.WATER.getExpYield());

        return report;
    }
    
    /**
     * Fertilizes the crop and returns a report detailing if it was successful
     * and feedback about the action. Fertilization fails if one of the
     * following conditions is met:
     *    1. The player has insufficient money.
     * @pre plantedCrop != null and is !withered
     * @param playerMoney   Player's current amount of money.
     * @return   Report about the action.
     * @see TileActionReportMessages
     */
    public TileActionReport fertilize(double playerMoney) {
        TileActionReport report = new TileActionReport(false, null, 0);

        if (playerMoney < TileActionData.FERTILIZE.getMoneyCost()) {
            report.setMessage(TileActionReportMessages.FERTILIZE_NO_MONEY.toString());
        } else {
            plantedCrop.fertilize();
            report.setSuccess(true);
            report.setMessage(TileActionReportMessages.FERTILIZE_SUCCESS.toString());
            report.setExpGained(TileActionData.FERTILIZE.getExpYield());
        }

        return report;
    }

    /**
     * Uses the shovel on the tile, which can have varying effects:
     *    1. It removes a crop if the tile has one.
     *    2. It unplows the tile if it is plowed and has no crop.
     *    3. It does nothing to an unplowed tile.
     * It only effectively fails if the player has insufficient money.
     * Otherwise, the player is still charged for using the shovel.
     * @param playerMoney   Player's current amount of money.
     * @return   Report about the action.
     * @see TileActionReportMessages
     */
    public TileActionReport dig(double playerMoney) {
        TileActionReport report = new TileActionReport(false, null, 0);

        if (playerMoney < TileActionData.DIG.getMoneyCost()) {
            report.setMessage(TileActionReportMessages.DIG_NO_MONEY.toString());
        } else if (!plowed) {
            report.setMessage(TileActionReportMessages.DIG_TILE_NOTHING.toString());
            report.setExpGained(TileActionData.DIG.getExpYield());
        } else if (plantedCrop != null) {
            plowed = false;
            plantedCrop = null;
            report.setSuccess(true);
            report.setMessage(TileActionReportMessages.DIG_CROP_REMOVED.toString());
            report.setExpGained(TileActionData.DIG.getExpYield());
        } else {
            plowed = false;
            report.setMessage(TileActionReportMessages.DIG_TILE_UNPLOWED.toString());
            report.setExpGained(TileActionData.DIG.getExpYield());
        } 

        return report;
    }

    /**
     * Calculates the total sell price for the harvested crop given the amount
     * of products produced and how much money is added per product sold.
     * @param productsProduced   The amount of products harvested.
     * @param bonusEarnings      Money added per product sold.
     * @return   Total selling price for the harvested crop.
     */
    public double calculateSellPrice(int productsProduced, double bonusEarnings) {
        double basePrice = productsProduced * (plantedCrop.getBaseSellPrice() + bonusEarnings);
        double waterBonus = basePrice * 0.2 * (plantedCrop.getCurrentWater() - 1);
        double fertilizerBonus = basePrice * 0.5 * plantedCrop.getCurrentFertilizer();
        double finalPrice = (basePrice + waterBonus + fertilizerBonus) * plantedCrop.getPremiumRate();
        return finalPrice;
    }

    /**
     * If the tile has a planted crop, it calls the crop's method for moving
     * on to the next day.
     * @see Crop#nextDay()
     */
    public void nextDay() {
        if (hasCrop()) {
            plantedCrop.nextDay();
        }
    }

    /**
     * Updates a crop's bonus water and fertilizer limits. Called when the
     * player successfully registers for a new farmer type/rank.
     * @param waterLimitIncrease        Amount to increase the water limit by.
     * @param fertilizerLimitIncrease   Amount to increase the fertilizer
     *                                  limit by.
     */
    public void updateCropStats(int waterLimitIncrease, int fertilizerLimitIncrease) {
        if (hasCrop()) {
            plantedCrop.setWaterLimit(plantedCrop.getWaterLimit() + waterLimitIncrease);
            plantedCrop.setFertilizerLimit(plantedCrop.getFertilizerLimit() + fertilizerLimitIncrease);
        }
    }

    /**
     * Prints the state of the crop, detailing if it has one and if it is
     * plowed. If it has a crop, it also prints the crop's state.
     * @see Crop#printState()
     */
    public void printState() {
        var s = "Has crop: " + hasCrop() + "\n"
              + "Is plowed: " + isPlowed() + "\n";
        
        System.out.print(s);

        if (hasCrop()) {
            System.out.println();
            plantedCrop.printState();
        }
    }

    public Crop getPlantedCrop() {
        return plantedCrop;
    }
    public boolean hasCrop() {
        return plantedCrop != null;
    }
    public boolean isPlowed() {
        return plowed;
    }

    // assumes that tile has a rock; unused so don't call for now
    // public TileActionReport mine() {
    //     TileActionReport report = new TileActionReport(false, null, 0);
    //     return report;
    // }
}
