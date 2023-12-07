/**
 * Contains feedback for a tile action/option.
 */
public class TileActionReport extends ActionReport {
    public static final String PLOW_SUCCESS = "The tile was successfully plowed!";
    public static final String PLOW_IS_PLOWED = "The tile is already plowed!";
    public static final String PLOW_HAS_CROP = "The tile is occupied by a crop!";
    public static final String PLOW_HAS_ROCK = "The tile is occupied by a rock!";

    public static final String PLANT_NOT_PLOWED = "The tile is not yet plowed!";
    public static final String PLANT_SUCCESS = "The crop was successfully planted on the tile!";
    public static final String PLANT_NO_MONEY = "You have insufficient money!";
    public static final String PLANT_HAS_CROP = "The tile is occupied by a crop!";
    public static final String PLANT_HAS_ROCK = "The tile is occupied by a rock!";
    public static final String PLANT_NO_ADJ_SPACE = "You cannot plant this crop here because some of the surrounding tiles are occupied!";

    public static final String WATER_SUCCESS = "The crop was successfully watered!";
    public static final String WATER_IS_WITHERED = "The crop is withered!";
    public static final String WATER_NO_CROP = "There is no crop to water!";

    public static final String FERTILIZE_SUCCESS = "The crop was successfully fertilized!";
    public static final String FERTILIZE_NO_CROP = "There is no crop to fertilize!";
    public static final String FERTILIZE_IS_WITHERED = "The crop is withered!";
    public static final String FERTILIZE_NO_MONEY = "You have insufficient money!";

    public static final String MINE_SUCCESS = "The rock was successfully removed!";
    public static final String MINE_NO_ROCK = "There is no rock to mine!";
    public static final String MINE_NO_MONEY = "You have insufficient money!";

    public static final String DIG_NO_MONEY = "You have insufficient money!";
    public static final String DIG_TILE_UNPLOWED = "The tile was unplowed!";
    public static final String DIG_TILE_NOTHING = "The tile is not plowed! There is nothing to do!";
    public static final String DIG_CROP_REMOVED = "The crop was removed!";
    public static final String DIG_HAS_ROCK = "The tile is occupied by a rock!";

    protected double expGained;

    /**
     * Creates a tile action report.
     */
    public TileActionReport() {
    }

    /**
     * Creates a tile action report and initializes its success state,
     * feedback message, money cost, and gained experience.
     * @param success     Whether the action was a success or not.
     * @param message     Feedback message for performing the action.
     * @param moneyCost   Money it costed to do the action.
     * @param expGained   Experience gained for performing the action.
     */
    public TileActionReport(boolean success, String message, double moneyCost, double expGained) {
        super(success, message, moneyCost);
        this.expGained = expGained;
    }

    @Override
	public String getDetails() {
        var s = super.getDetails()
              + "Experience Gained: " + expGained + "\n";
        
        return s;
	}
    
    public double getExpGained() {
        return expGained;
    }
    public void setExpGained(double expGained) {
        this.expGained = expGained;
    }
}
