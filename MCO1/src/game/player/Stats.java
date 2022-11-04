package game.player;

/**
 * Records some statistics about the player's actions.
 */
public class Stats {
    private int timesPlanted;
    private int timesPlowed;
    private int timesWatered;
    private int timesFertilized;
    private int timesHarvested;
    private int timesDug;
    // TODO: private int timesMined;

    /**
     * Creates a new group of statistics.
     */
    public Stats() {
    }

    /**
     * Creates a new group of statistics and initializes them using the given
     * data.
     * @param timesPlanted      Times the player has planted a crop.
     * @param timesPlowed       Times the player has plowed a tile.
     * @param timesWatered      Times the player has watered a crop.
     * @param timesFertilized   Times the player has fertilized a crop.
     * @param timesHarvested    Times the player has harvested a crop.
     * @param timesDug          Times the player has dug using the shovel.
     */
    public Stats(int timesPlanted, int timesPlowed, int timesWatered, int timesFertilized, int timesHarvested,
            int timesDug) {
        this.timesPlanted = timesPlanted;
        this.timesPlowed = timesPlowed;
        this.timesWatered = timesWatered;
        this.timesFertilized = timesFertilized;
        this.timesHarvested = timesHarvested;
        this.timesDug = timesDug;
    }

    /**
     * Increases the number of times the player has planted a crop by one.
     * @return   The new number of times the player has planted a crop.
     */
    public int addTimesPlanted() {
        return ++timesPlanted;
    }

    /**
     * Increases the number of times the player has plowed a tile by one.
     * @return   The new number of times the player has plowed a tile.
     */
    public int addTimesPlowed() {
        return ++timesPlowed;
    }

    /**
     * Increases the number of times the player has watered a crop by one.
     * @return   The new number of times the player has watered a crop.
     */
    public int addTimesWatered() {
        return ++timesWatered;
    }
    
    /**
     * Increases the number of times the player has fertilized a crop by one.
     * @return   The new number of times the player has fertilized a crop.
     */
    public int addTimesFertilized() {
        return ++timesFertilized;
    }

    /**
     * Increases the number of times the player has harvested a crop by one.
     * @return   The new number of times the player has harvested a crop.
     */
    public int addTimesHarvested() {
        return ++timesHarvested;
    }

    /**
     * Increases the number of times the player has dug using the shovel by
     * one.
     * @return   The new number of times the player has dug using the shovel.
     */
    public int addTimesDug() {
        return ++timesDug;
    }

    /**
     * Returns a string of the player's statistics.
     */
    @Override
    public String toString() {
        var s = "Times Planted: " + timesPlanted + "\n"
              + "Times Plowed: " + timesPlowed + "\n"
              + "Times Watered: " + timesWatered + "\n"
              + "Times Fertilized: " + timesFertilized + "\n"
              + "Times Harvested: " + timesHarvested + "\n"
              + "Times Dug: " + timesDug + "\n";
        
        return s;
    }
    
    public int getTimesPlanted() {
        return timesPlanted;
    }
    public void setTimesPlanted(int timesPlanted) {
        this.timesPlanted = timesPlanted;
    }
    public int getTimesPlowed() {
        return timesPlowed;
    }
    public void setTimesPlowed(int timesPlowed) {
        this.timesPlowed = timesPlowed;
    }
    public int getTimesWatered() {
        return timesWatered;
    }
    public void setTimesWatered(int timesWatered) {
        this.timesWatered = timesWatered;
    }
    public int getTimesFertilized() {
        return timesFertilized;
    }
    public void setTimesFertilized(int timesFertilized) {
        this.timesFertilized = timesFertilized;
    }
    public int getTimesHarvested() {
        return timesHarvested;
    }
    public void setTimesHarvested(int timesHarvested) {
        this.timesHarvested = timesHarvested;
    }
    public int getTimesDug() {
        return timesDug;
    }
    public void setTimesDug(int timesDug) {
        this.timesDug = timesDug;
    }    
}
