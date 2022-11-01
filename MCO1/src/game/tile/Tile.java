package game.tile;

import game.crop.*;

public class Tile {
    private Crop plantedCrop;
    private boolean plowed;


    public double calculateSellPrice(int productsProduced, double bonusEarnings) {
        double basePrice = productsProduced * (plantedCrop.getBaseSellPrice() + bonusEarnings);
        double waterBonus = basePrice * 0.2 * (plantedCrop.getCurrentWater() - 1);
        double fertilizerBonus = basePrice * 0.5 * plantedCrop.getCurrentFertilizer();
        double finalPrice = (basePrice + waterBonus + fertilizerBonus) * plantedCrop.getPremiumRate();
        return finalPrice;
    }

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

    // assumes that tile is plowed
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

    // assumes that plantedCrop != null and isHarvestable()
    public HarvestCropReport harvest(double bonusEarnings) {
        HarvestCropReport report = plantedCrop.harvest();
        plantedCrop = null;
        plowed = false;

        // products produced and exp yield are calculated in plantedCrop's harvest() method
        double sellPrice = calculateSellPrice(report.getProductsProduced(), bonusEarnings);
        report.setProfit(sellPrice);
        
        return report;
    }

    // assumes that plantedCrop != null and is !withered
    public TileActionReport water() {
        plantedCrop.water();
        TileActionReport report = new TileActionReport(true, null, 0);
        report.setMessage(TileActionReportMessages.WATER_SUCCESS.toString());
        report.setExpGained(TileActionData.WATER.getExpYield());

        return report;
    }

    // assumes that plantedCrop != null and is !withered
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

    // assumes that tile has a rock; unused so don't call for now
    public TileActionReport mine() {
        TileActionReport report = new TileActionReport(false, null, 0);
        return report;
    }

    public TileActionReport dig(double playerMoney) {
        TileActionReport report = new TileActionReport(false, null, 0);

        if (playerMoney < TileActionData.DIG.getMoneyCost()) {
            report.setMessage(TileActionReportMessages.DIG_NO_MONEY.toString());
        } else if (!plowed) {
            report.setMessage(TileActionReportMessages.DIG_TILE_NOTHING.toString());
            report.setExpGained(TileActionData.DIG.getExpYield());
        } else if (plantedCrop != null) {
            plantedCrop = null;
            report.setMessage(TileActionReportMessages.DIG_CROP_REMOVED.toString());
            report.setExpGained(TileActionData.DIG.getExpYield());
        } else {
            plowed = false;
            report.setSuccess(true);
            report.setMessage(TileActionReportMessages.DIG_TILE_UNPLOWED.toString());
            report.setExpGained(TileActionData.DIG.getExpYield());
        } 

        return report;
    }

    public void nextDay() {
        if (hasCrop()) {
            plantedCrop.nextDay();
        }
    }
    
    public void printState() {
        var s = "Has crop: " + hasCrop() + "\n"
              + "Is plowed: " + isPlowed() + "\n";
        
        System.out.print(s);
    }

    public void updateCropStats(int waterLimitIncrease, int fertilizerLimitIncrease) {
        if (hasCrop()) {
            plantedCrop.setWaterLimit(plantedCrop.getWaterLimit() + waterLimitIncrease);
            plantedCrop.setFertilizerLimit(plantedCrop.getFertilizerLimit() + fertilizerLimitIncrease);
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
}
