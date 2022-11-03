package game.player;

public class Stats {
    private int timesPlanted;
    private int timesPlowed;
    private int timesWatered;
    private int timesFertilized;
    private int timesHarvested;
    private int timesDug;
    // TODO: private int timesMined;

    public int addTimesPlanted() {
        return ++timesPlanted;
    }

    public int addTimesPlowed() {
        return ++timesPlowed;
    }

    public int addTimesWatered() {
        return ++timesWatered;
    }

    public int addTimesFertilized() {
        return ++timesFertilized;
    }

    public int addTimesHarvested() {
        return ++timesHarvested;
    }

    public int addTimesDug() {
        return ++timesDug;
    }

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
