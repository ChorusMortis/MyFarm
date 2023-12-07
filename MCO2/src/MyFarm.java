/**
 * Represents the main model of the game where most of the logic go.
 */
public class MyFarm {
    public static final String NO_TILE_SELECTED = "No tile selected.";

    public static final Crop cheapestCrop = new Turnip(); // change if necessary

    private Tile[][] tiles;
    private Stats playerStats;
    private boolean running;
    private String reasonForGameEnd;

    /**
     * Creates and instantiates the model given the number of rocks to start
     * with.
     * @param numberOfRocks   The number of rocks to start with.
     */
    public MyFarm(int numberOfRocks) {
        running = true;
        
        tiles = new Tile[5][10];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(null, false, false, i, j);
            }
        }

        // NOTE: it is highly improbable that this will generate fewer rocks than required, but theoretically possible
        generateRocks(numberOfRocks);

        playerStats = Stats.createDefaultRank();
        playerStats.setObjectCoins(100);
    }

    /**
     * Checks the game-ending conditions of the game and returns whether the
     * game should continue or not.
     * @return   Whether the game should continue or not.
     */
    public boolean shouldGameContinue() {
        if (!canPlayerAffordCheapestCrop() && !isThereAtLeastOneNonWitheredCrop()) {
            running = false;
            reasonForGameEnd = "No growing crops and cannot afford to buy new ones.";
        } else if (areAllTilesOccupiedByWitheredCrops()) {
            running = false;
            reasonForGameEnd = "All tiles are occupied by withered crops.";
        }

        return running;
    }

    /**
     * Checks if the player can still afford the cheapest crop. Factors in the
     * seed cost reduction of the player.
     * @return   Whether the player can afford the cheapest crop.
     */
    private boolean canPlayerAffordCheapestCrop() {
        return playerStats.getObjectCoins() >= cheapestCrop.getBaseSeedCost() - playerStats.getSeedCostReduction();
    }

    /**
     * Checks if there is at least non-withered crop in the farm.
     * @return   Whether there is at least one non-withered crop in the farm.
     */
    private boolean isThereAtLeastOneNonWitheredCrop() {
        for (Tile[] tileArray : tiles) {
            for (Tile tile : tileArray) {
                if (tile.hasCrop() && !tile.getPlantedCrop().isWithered()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the farm lot is entirely filled with withered crops. Does
     * not ignore rocks and empty tiles.
     * @return   Whether the farm is entirely filled with withered crops or
     *           not.
     */
    private boolean areAllTilesOccupiedByWitheredCrops() {
        for (Tile[] tileArray : tiles) {
            for (Tile tile : tileArray) {
                if (!tile.hasCrop()) {
                    return false;
                }

                if (!tile.getPlantedCrop().isWithered()) {
                    return false;                    
                }
            }
        }
        return true;
    }

    /**
     * Attempts to generate rocks on tiles in the farm using the given number.
     * Per rock, there are 30 attempts made to generate it. Otherwise, it fails
     * and continues. While it is highly improbable, it is theoretically
     * possible to generate rocks less than the given number.
     * @param numberOfRocks   The number of rocks to be generated.
     * @return   The number of rocks generated.
     */
    private int generateRocks(int numberOfRocks) {
        int generatedRocks = 0;
        int rowUpperBound = tiles.length - 1;
        int columnUpperBound = tiles[0].length - 1;

        for (int i = 0; i < numberOfRocks; i++) {
            for (int attempt = 0; attempt < 30; attempt++) {
                int row = generateRandomNumber(0, rowUpperBound);
                int column = generateRandomNumber(0, columnUpperBound);
                Tile tile = tiles[row][column];

                if (tile.hasRock()) {
                    continue;
                }
                
                tile.setRock(true);
                generatedRocks++;
                break;
            }
        }

        return generatedRocks;
    }

    /**
     * Attempts to plow the given tile.
     * @param tile   The tile to be plowed.
     * @return   A report containing feedback about the action.
     */
    public ActionReport plowTile(Tile tile) {
        ActionReport report = new TileActionReport(false, NO_TILE_SELECTED, 0, 0);
        if (tile != null) {
            report = tile.plow();
        }
        return report;
    }

    /**
     * Attempts to plant the given crop on the given tile.
     * @param tile   The tile on which the crop is to be planted.
     * @param crop   The crop to be planted on the tile.
     * @return   A report containing feedback about the action.
     */
    public ActionReport plantCropOnTile(Tile tile, Crop crop) {
        ActionReport report = new TileActionReport(false, NO_TILE_SELECTED, 0, 0);

        if (tile == null) {
            return report;
        }

        if (crop == null) {
            report.setMessage("No crop to plant.");
            return report;
        }

        if (!isCropPlantableOnTile(crop, tile)) {
            report.setMessage("The crop is not plantable on this tile!");
            return report;
        }

        crop.updateWaterAndFertilizerLimits(playerStats.getWaterLimitIncrease(),
                playerStats.getFertilizerLimitIncrease());
        report = tile.plant(crop, playerStats.getObjectCoins(), playerStats.getSeedCostReduction());
        return report;
    }

    /**
     * Attempts to harvest the crop on the given tile.
     * @param tile   The tile from which the crop to be harvested resides.
     * @return   A report containing feedback about the action.
     */
    public ActionReport harvestCropFromTile(Tile tile) {
        ActionReport report = new HarvestCropReport(false, NO_TILE_SELECTED, 0, 0, 0, 0);

        if (tile == null) {
            return report;
        }

        report = tile.harvest(playerStats.getBonusEarnings());
        return report;
    }

    /**
     * Attempts to water the crop on the given tile.
     * @param tile   The tile from which the crop to be watered resides.
     * @return   A report containing feedback about the action.
     */
    public ActionReport waterCropOnTile(Tile tile) {
        ActionReport report = new TileActionReport(false, NO_TILE_SELECTED, 0, 0);

        if (tile == null) {
            return report;
        }

        report = tile.water();
        return report;
    }

    /**
     * Attempts to fertilize the crop on the given tile.
     * @param tile   The tile from which the crop to be fertilized resides.
     * @return   A report containing feedback about the action.
     */
    public ActionReport fertilizeCropOnTile(Tile tile) {
        ActionReport report = new TileActionReport(false, NO_TILE_SELECTED, 0, 0);

        if (tile == null) {
            return report;
        }

        report = tile.fertilize(playerStats.getObjectCoins());
        return report;
    }

    /**
     * Attempts to dig something on the given tile.
     * @param tile   The tile to be used the shovel on.
     * @return   A report containing feedback about the action.
     */
    public ActionReport digSomethingOnTile(Tile tile) {
        ActionReport report = new TileActionReport(false, NO_TILE_SELECTED, 0, 0);

        if (tile == null) {
            return report;
        }

        report = tile.dig(playerStats.getObjectCoins());
        return report;
    }

    /**
     * Attempts to mine the rock on the given tile.
     * @param tile   The tile to be used the pickaxe on.
     * @return   A report containing feedback about the action.
     */
    public ActionReport mineRockFromTile(Tile tile) {
        ActionReport report = new TileActionReport(false, NO_TILE_SELECTED, 0, 0);

        if (tile == null) {
            return report;
        }

        report = tile.mine(playerStats.getObjectCoins());
        return report;
    }

    /**
     * Moves on to the next day and lets crops grow.
     * @return   A report containing feedback about the action.
     */
    public ActionReport moveOnToNextDay() {
        for (Tile[] tileArray : tiles) {
            for (Tile tile : tileArray) {
                tile.nextDay();
            }
        }
        return new ActionReport(true, "Moved on to the next day!", 0);
    }

    /**
     * Attempts to register for a new farmer rank/type.
     * @param newStats   The new stats of the player.
     * @return   A report containing feedback about the action.
     */
    public ActionReport registerForRank(Stats newStats) {
        ActionReport report = new RankRegistrationReport(false, "No rank selected.", 0, null);

        if (newStats == null) {
            return report;
        }

        int newRankTier = newStats.getRankTier();
        int currentRankTier = playerStats.getRankTier();
        double newRankCost = newStats.getRankCost();
        if (newRankTier < currentRankTier) {
            report.setMessage("You have already registered for a higher rank!");
        } else if (newRankTier == currentRankTier) {
            report.setMessage("You are currently registered for this rank!");
        } else if (playerStats.getLevel() < newStats.getRankRequiredLevel()) {
            report.setMessage("You have not reached the required level for the rank!");
        } else if (playerStats.getObjectCoins() < newRankCost) {
            report.setMessage("You have insufficient money to register for this rank!");
        } else {
            report.setSuccess(true);
            report.setMessage("Registration successful!");
            report.setMoneyCost(newRankCost);
            ((RankRegistrationReport)report).setNewRank(newStats);
        }

        return report;
    }

    /**
     * Updates the player stats from the given report. If the player
     * registered successfully for a new farmer type/rank, all tiles on the
     * farm have their water and fertilizer limits increased but without the
     * bonuses stacking.
     * @param report   A report containing feedback from the action the player
     *                 just did.
     * @return   The updated stats of the player.
     */
    public Stats updatePlayerStatsFromReport(ActionReport report) {
        if (report instanceof RankRegistrationReport) {
            RankRegistrationReport tempReport = (RankRegistrationReport)report;
            Stats newStats = tempReport.getNewRank();
            if (newStats != null) {
                int oldWaterLimit = playerStats.getWaterLimitIncrease();
                int oldFertilizerLimit = playerStats.getFertilizerLimitIncrease();
                int waterLimitIncrease = newStats.getWaterLimitIncrease() - oldWaterLimit;
                int fertilizerLimitIncrease = newStats.getFertilizerLimitIncrease() - oldFertilizerLimit;
                playerStats.updateRank(newStats);
                updateAllCropLimits(waterLimitIncrease, fertilizerLimitIncrease);
            }
        }

        if (report instanceof HarvestCropReport) {
            playerStats.addMoney(((HarvestCropReport)report).getProfit());
        }

        if (report instanceof TileActionReport) {
            playerStats.addExperience(((TileActionReport)report).getExpGained());
        }

        playerStats.deductMoney(report.getMoneyCost());
        return playerStats;
    }

    /**
     * Increases all the existing crops' (in the farm) water and fertilizer
     * limits by the given amounts.
     * @param waterLimitIncrease        Amount to increase the crops' water
     *                                  limit by.
     * @param fertilizerLimitIncrease   Amount to increase the crops'
     *                                  fertilizer limit by.
     */
    private void updateAllCropLimits(int waterLimitIncrease, int fertilizerLimitIncrease) {
        for (Tile[] tileArray : tiles) {
            for (Tile tile : tileArray) {
                updateCropLimits(tile, waterLimitIncrease, fertilizerLimitIncrease);
            }
        }
    }

    /**
     * Increases a crop's water and fertilizer limits by the given amounts.
     * @param tile                      The tile that contains the crop.
     * @param waterLimitIncrease        Amount to increase the crop's water
     *                                  limit by.
     * @param fertilizerLimitIncrease   Amount to increase the crop's
     *                                  fertilizer limit by.
     */
    private void updateCropLimits(Tile tile, int waterLimitIncrease, int fertilizerLimitIncrease) {
        tile.updateCropLimits(waterLimitIncrease, fertilizerLimitIncrease);
    }

    /**
     * Checks if the given crop is plantable on the given tile considering its
     * type and planting conditions. This does not check if the tile is
     * occupied, however.
     * @param crop   The crop to be planted.
     * @param tile   The tile where the crop is to be planted.
     * @return   Whether the crop is plantable on the tile.
     */
    private boolean isCropPlantableOnTile(Crop crop, Tile tile) {
        if (!crop.getType().equals("Fruit Tree")) {
            return true;
        }

        if (isTileOnEdge(tile)) {
            return false;
        }

        return isAllAdjacentTilesUnoccupied(tile);
    }

    /**
     * Checks all 8 adjacent sides of the given tile to see if they are all
     * unoccupied. This assumes that the given tile is not on the edge of
     * the farm.
     * @param tile   The tile which adjacent sides are to be checked.
     * @return   Whether all the adjacent sides of the tile are unoccupied.
     */
    private boolean isAllAdjacentTilesUnoccupied(Tile tile) {
        return !getAdjacentNorthTile(tile).isOccupied() &&
                !getAdjacentEastTile(tile).isOccupied() &&
                !getAdjacentWestTile(tile).isOccupied() &&
                !getAdjacentSouthTile(tile).isOccupied() &&
                !getAdjacentNortheastTile(tile).isOccupied() &&
                !getAdjacentNorthwestTile(tile).isOccupied() &&
                !getAdjacentSoutheastTile(tile).isOccupied() &&
                !getAdjacentSouthwestTile(tile).isOccupied();
    }

    /**
     * Checks if the given tile is on the edge of the farm.
     * @param tile   The tile to be checked.
     * @return   Whether the tile is on the edge or not.
     */
    private boolean isTileOnEdge(Tile tile) {
        if (tile.getRow() % (tiles.length - 1) == 0) {
            return true;
        }

        if (tile.getColumn() % (tiles[0].length - 1) == 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns the northern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The northern adjacent tile.
     */
    private Tile getAdjacentNorthTile(Tile tile) {
        return tiles[tile.getRow() - 1][tile.getColumn()];
    }

    /**
     * Returns the southern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The southern adjacent tile.
     */
    private Tile getAdjacentSouthTile(Tile tile) {
        return tiles[tile.getRow() + 1][tile.getColumn()];
    }

    /**
     * Returns the western adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The western adjacent tile.
     */
    private Tile getAdjacentWestTile(Tile tile) {
        return tiles[tile.getRow()][tile.getColumn() - 1];
    }

    /**
     * Returns the eastern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The eastern adjacent tile.
     */
    private Tile getAdjacentEastTile(Tile tile) {
        return tiles[tile.getRow()][tile.getColumn() + 1];
    }

    /**
     * Returns the northwestern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The northwestern adjacent tile.
     */
    private Tile getAdjacentNorthwestTile(Tile tile) {
        return getAdjacentWestTile(getAdjacentNorthTile(tile));
    }

    /**
     * Returns the northeastern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The northeastern adjacent tile.
     */
    private Tile getAdjacentNortheastTile(Tile tile) {
        return getAdjacentEastTile(getAdjacentNorthTile(tile));
    }

    /**
     * Returns the southwestern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The southwestern adjacent tile.
     */
    private Tile getAdjacentSouthwestTile(Tile tile) {
        return getAdjacentWestTile(getAdjacentSouthTile(tile));
    }

    /**
     * Returns the southeastern adjacent tile relative to the given tile.
     * Assumes that the given tile is not on the edge.
     * @param tile   The tile to start off from.
     * @return   The southeastern adjacent tile.
     */
    private Tile getAdjacentSoutheastTile(Tile tile) {
        return getAdjacentEastTile(getAdjacentSouthTile(tile));
    }

    /**
     * Generates a pseudorandom integer between the given lower bound and
     * upper bound, both inclusive.
     * @param lowerBound   The lower bound.
     * @param upperBound   The uppwer bound.
     * @return   The pseudorandomly-generated integer.
     */
    private int generateRandomNumber(int lowerBound, int upperBound) {
        return (int)Math.floor((Math.random() * (upperBound - lowerBound + 1)) + lowerBound);
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public Stats getPlayerStats() {
        return playerStats;
    }
    public boolean isRunning() {
        return running;
    }
    public String getReasonForGameEnd() {
        return reasonForGameEnd;
    }
}
