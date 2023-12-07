/**
 * Represents each tile in the farmer's farm that can hold and grow a crop.
 */
public class Tile {
    public static final double PLOW_COST = 0;
    public static final double PLOW_EXP = 0.5;

    public static final double PLANT_EXP = 0; // no exp for just planting

    public static final double HARVEST_COST = 0;

    public static final double WATER_COST = 0;
    public static final double WATER_EXP = 0.5;

    public static final double FERTILIZE_COST = 10;
    public static final double FERTILIZE_EXP = 4;

    public static final double MINE_COST = 50;
    public static final double MINE_EXP = 15;

    public static final double DIG_COST = 7;
    public static final double DIG_EXP = 2;

    private Crop plantedCrop;
    private boolean plowed;
    private boolean rock;
    private int row;
    private int column;
    
    /**
     * Creates a tile.
     */
    public Tile() {
    }
    
    /**
     * Creates a tile, initializes it with the given crop, plowed state, and
     * position.
     * @param plantedCrop   The crop planted on the tile.
     * @param plowed        Whether the crop is plowed or not.
     * @param rock          Whether the tile has a rock or not.
     * @param row           Its row position.
     * @param column        Its column position.
     */
    public Tile(Crop plantedCrop, boolean plowed, boolean rock, int row, int column) {
        this.plantedCrop = plantedCrop;
        this.plowed = plowed;
        this.rock = rock;
        this.row = row;
        this.column = column;
    }

    /**
     * Plows the tile and returns a report detailing if it was successful and
     * feedback about the action. Plowing fails if one of the following
     * conditions is met:
     *    (1) The tile is already plowed.
     *    (2) The tile already has a crop.
     *    (3) The tile has a rock.
     * @return   Report about the action.
     */
    public TileActionReport plow() {
        TileActionReport report = new TileActionReport(false, null, 0, 0);

        if (this.isPlowed()) {
            report.setMessage(TileActionReport.PLOW_IS_PLOWED);
        } else if (this.hasCrop()) {
            report.setMessage(TileActionReport.PLOW_HAS_CROP);
        } else if (this.hasRock()) {
            report.setMessage(TileActionReport.PLOW_HAS_ROCK);
        } else {
            plowed = true;
            report.setSuccess(true);
            report.setMessage(TileActionReport.PLOW_SUCCESS);
            report.setExpGained(PLOW_EXP);
        }

        return report;
    }
    
    /**
     * Plants the given crop and returns a report detailing if it was
     * successful and feedback about the action. Planting fails if one of the
     * following conditions is met:
     *    (1) The tile has a rock.
     *    (2) The tile already has a crop.
     *    (3) The tile is not plowed.
     *    (4) The player has insufficient money.
     * @param crop                The crop to be planted.
     * @param playerMoney         Player's current amount of money.
     * @param seedCostReduction   Discount for the purchase.
     * @return   Report about the action.
     */
    public TileActionReport plant(Crop crop, double playerMoney, double seedCostReduction) {
        TileActionReport report = new TileActionReport(false, null, 0, 0);
        double discountedCost = crop.getBaseSeedCost() - seedCostReduction;

        if (this.hasRock()) {
            report.setMessage(TileActionReport.PLANT_HAS_ROCK);
        } else if (this.hasCrop()) {
            report.setMessage(TileActionReport.PLANT_HAS_CROP);
        } else if (!this.isPlowed()) {
            report.setMessage(TileActionReport.PLANT_NOT_PLOWED);
        } else if (playerMoney < discountedCost) {
            report.setMessage(TileActionReport.PLANT_NO_MONEY);
        } else {
            plantedCrop = crop;
            report.setSuccess(true);
            report.setMessage(TileActionReport.PLANT_SUCCESS);
            report.setMoneyCost(discountedCost);
            report.setExpGained(PLANT_EXP);
        }

        return report;
    }

    /**
     * Harvests the crop, removes it from the tile, and returns a report
     * detailing if it was successful and feedback about the action.
     * Harvesting fails if one of the following conditions is met:
     *    (1) The tile has no crop.
     *    (2) The planted crop is not harvestable.
     * @param bonusEarnings   Added money per product sold.
     * @return   Report about the action.
     */
    public HarvestCropReport harvest(double bonusEarnings) {
        HarvestCropReport report = new HarvestCropReport(false, null, 0, 0, 0, 0);

        if (!this.hasCrop()) {
            // no need to check if there's a rock because there's no crop anyway
            report.setMessage(HarvestCropReport.HARVEST_NO_CROP);
        } else if (!plantedCrop.isHarvestable()) {
            report.setMessage(HarvestCropReport.HARVEST_NOT_HARVESTABLE);
        } else {
            report.setSuccess(true);
            report.setMessage(HarvestCropReport.HARVEST_SUCCESS);
            int productsProduced = plantedCrop.getRandomYield();
            report.setProductsProduced(productsProduced);
            report.setExpGained(plantedCrop.getExpYield() * productsProduced);
            double sellPrice = plantedCrop.calculateSellPrice(productsProduced, bonusEarnings);
            report.setProfit(sellPrice);
            plantedCrop = null;
            plowed = false;
        }
        
        return report;
    }

    /**
     * Waters the crop and returns a report detailing if it was successful and
     * feedback about the action. Watering fails if one of the following
     * conditions is met:
     *    (1) The tile has no crop.
     *    (2) The planted crop has withered.
     * @return   Report about the action.
     */
    public TileActionReport water() {
        TileActionReport report = new TileActionReport(false, null, 0, 0);

        if (!this.hasCrop()) {
            // no need to check if there's a rock because there's no crop anyway
            report.setMessage(TileActionReport.WATER_NO_CROP);
        } else if (this.getPlantedCrop().isWithered()) {
            report.setMessage(TileActionReport.WATER_IS_WITHERED);
        } else {
            plantedCrop.water();
            report.setSuccess(true);
            report.setMessage(TileActionReport.WATER_SUCCESS);
            report.setExpGained(WATER_EXP);
        }

        return report;
    }
    
    /**
     * Fertilizes the crop and returns a report detailing if it was successful
     * and feedback about the action. Fertilization fails if one of the
     * following conditions is met:
     *    (1) The tile has no crop.
     *    (2) The crop has withered.
     *    (3) The player has insufficient money.
     * @param playerMoney   Player's current amount of money.
     * @return   Report about the action.
     */
    public TileActionReport fertilize(double playerMoney) {
        TileActionReport report = new TileActionReport(false, null, 0, 0);

        if (!this.hasCrop()) {
            // no need to check if there's a rock because there's no crop anyway
            report.setMessage(TileActionReport.FERTILIZE_NO_CROP);
        } else if (this.getPlantedCrop().isWithered()) {
            report.setMessage(TileActionReport.FERTILIZE_IS_WITHERED);
        } else if (playerMoney < FERTILIZE_COST) {
            report.setMessage(TileActionReport.FERTILIZE_NO_MONEY);
        } else {
            plantedCrop.fertilize();
            report.setSuccess(true);
            report.setMessage(TileActionReport.FERTILIZE_SUCCESS);
            report.setMoneyCost(FERTILIZE_COST);
            report.setExpGained(FERTILIZE_EXP);
        }

        return report;
    }

    /**
     * Uses the shovel on the tile, which can have varying effects:
     *    (1) It removes a crop if the tile has one.
     *    (2) It unplows the tile if it is plowed and has no crop.
     *    (3) It does nothing to an unplowed tile or a tile with a rock.
     * The player is still charged money for failing to use the shovel
     * properly. The only time the player does not get charged is when they
     * have insufficient money. Returns a report detailing if it was
     * successful and feedback about the action.
     * @param playerMoney   Player's current amount of money.
     * @return   Report about the action.
     */
    public TileActionReport dig(double playerMoney) {
        TileActionReport report = new TileActionReport(false, null, 0, 0);

        if (playerMoney < DIG_COST) {
            report.setMessage(TileActionReport.DIG_NO_MONEY);
        } else if (this.hasRock()) {
            report.setMessage(TileActionReport.DIG_HAS_ROCK);
            report.setMoneyCost(DIG_COST);
        } else if (!this.isPlowed()) {
            report.setMessage(TileActionReport.DIG_TILE_NOTHING);
            report.setMoneyCost(DIG_COST);
            report.setExpGained(DIG_EXP);
        } else if (this.hasCrop()) {
            plowed = false;
            plantedCrop = null;
            report.setSuccess(true);
            report.setMessage(TileActionReport.DIG_CROP_REMOVED);
            report.setMoneyCost(DIG_COST);
            report.setExpGained(DIG_EXP);
        } else { // this.isPlowed() and this.hasCrop()
            plowed = false;
            report.setMessage(TileActionReport.DIG_TILE_UNPLOWED);
            report.setMoneyCost(DIG_COST);
            report.setExpGained(DIG_EXP);
        }

        return report;
    }

    /**
     * Mines the rock from the tile and returns a report detailing if it was
     * successful and feedback about the action. Mining fails if one of the
     * following conditions is met:
     *    (1) The tile has no rock.
     *    (2) The player has insufficient money.
     * @param playerMoney   Player's current amount of money.
     * @return   Report about the action.
     */
    public TileActionReport mine(double playerMoney) {
        TileActionReport report = new TileActionReport(false, null, 0, 0);

        if (!this.hasRock()) {
            report.setMessage(TileActionReport.MINE_NO_ROCK);
        } else if (playerMoney < MINE_COST) {
            report.setMessage(TileActionReport.MINE_NO_MONEY);
        } else {
            rock = false;
            report.setSuccess(true);
            report.setMessage(TileActionReport.MINE_SUCCESS);
            report.setMoneyCost(MINE_COST);
            report.setExpGained(MINE_EXP);
        }

        return report;
    }

    /**
     * If the tile has a planted crop, it calls the crop's method for moving
     * on to the next day.
     * @see Crop#nextDay()
     */
    public void nextDay() {
        if (this.hasCrop()) {
            plantedCrop.nextDay();
        }
    }

    /**
     * Updates a crop's bonus water and fertilizer limits. Called when the
     * player successfully registers for a new farmer type/rank to affect
     * existing crops retroactively from the stats change.
     * @param waterLimitIncrease        Amount to increase the water limit by.
     * @param fertilizerLimitIncrease   Amount to increase the fertilizer
     *                                  limit by.
     */
    public void updateCropLimits(int waterLimitIncrease, int fertilizerLimitIncrease) {
        if (this.hasCrop()) {
            plantedCrop.updateWaterAndFertilizerLimits(waterLimitIncrease, fertilizerLimitIncrease);
        }
    }

    /**
     * Returns a multiline string of the state of the tile, such as whether it
     * has a rock, is plowed, and has a crop. If it has a crop, the crop's
     * state is appended to the string.
     * @see Crop#getDetails()
     */
    public String getDetails() {
        var s = "Has rock: " + hasRock() + "\n"
              + "Is plowed: " + isPlowed() + "\n"
              + "Has crop: " + hasCrop() + "\n";

        if (this.hasCrop()) {
            s += "\n"
               + plantedCrop.getDetails();
        }

        return s;
    }

    /**
     * Returns true if the rock has a crop. Otherwise, false.
     * @return   True if the tile has a crop. Otherwise, false.
     */
    public boolean hasCrop() {
        return plantedCrop != null;
    }

    /**
     * Returns true if the rock is occupied by a crop or rock.
     * Otherwise, false.
     * @return   True if the tile has a crop or rock. Otherwise, false.
     */
    public boolean isOccupied() {
        return hasCrop() || hasRock();
    }

    public Crop getPlantedCrop() {
        return plantedCrop;
    }
    public void setPlantedCrop(Crop plantedCrop) {
        this.plantedCrop = plantedCrop;
    }
    public boolean isPlowed() {
        return plowed;
    }
    public void setPlowed(boolean plowed) {
        this.plowed = plowed;
    }
    public boolean hasRock() {
        return rock;
    }
    public void setRock(boolean rock) {
        this.rock = rock;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
}
