package game.player;

public class Stats {
    private int timesPlanted;
    private int timesPlowed;
    private int timesWatered;
    private int timesFertilized;
    private int timesHarvested;


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
}
