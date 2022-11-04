package game.tile;

/**
 * Holds message strings for feedback in a tile action report.
 */
public enum TileActionReportMessages {
    /**
     * Message for successful plowing of the tile.
     */
    PLOW_SUCCESS("The tile was successfully plowed!"),

    /**
     * Message for attempting to plow an already plowed tile.
     */
    PLOW_IS_PLOWED("The tile is already plowed!"),

    /**
     * Message for attempting to plow a tile with a crop.
     */
    PLOW_HAS_CROP("The tile is occupied by a crop!"),

    /**
     * Message for attempting to plow a tile with a rock.
     */
    PLOW_HAS_ROCK("The tile is occupied by a rock!"),


    /**
     * Message for successful planting of a crop.
     */
    PLANT_SUCCESS("The crop was successfully planted on the tile!"),

    /**
     * Message for attempting to plant a crop while having insufficient money.
     */
    PLANT_NO_MONEY("You have insufficient money!"),

    /**
     * Message for attempting to plant a crop on a tile with a crop.
     */
    PLANT_HAS_CROP("The tile is occupied by a crop!"),

    /**
     * Message for attempting to plant a crop on a tile with a rock.
     */
    PLANT_HAS_ROCK("The tile is occupied by a rock!"),

    /**
     * Message for attempting to plant a crop which has specific adjacent
     * space requirements which were not met.
     */
    PLANT_NO_ADJ_SPACE("You cannot plant this crop here because some of the surrounding tiles are occupied!"),


    /**
     * Message for successfully watering a crop.
     */
    WATER_SUCCESS("The crop was successfully watered!"),

    /**
     * Message for attempting to water a crop but the tile has a rock.
     */
    WATER_HAS_ROCK("The tile is occupied by a rock!"),


    /**
     * Message for successfully fertilizing a crop.
     */
    FERTILIZE_SUCCESS("The crop was successfully fertilized!"),

    /**
     * Message for attempting to fertilize a crop while having insufficient
     * money.
     */
    FERTILIZE_NO_MONEY("You have insufficient money!"),

    /**
     * Message for attempting to fertilize a crop but the tile has a rock.
     */
    FERTILIZE_HAS_ROCK("The tile is occupied by a rock!"),


    /**
     * Message for successfully removing a rock from a tile.
     */
    MINE_SUCCESS("The rock was successfully removed!"),

    /**
     * Message for attempting to mine a rock on a tile while having
     * insufficient money.
     */
    MINE_NO_MONEY("You have insufficient money!"),


    /**
     * Message for attempting to use the shovel while having insufficient
     * money.
     */
    DIG_NO_MONEY("You have insufficient money!"),

    /**
     * Message for using the shovel on a plowed tile with no crop.
     */
    DIG_TILE_UNPLOWED("The tile was unplowed!"),

    /**
     * Message for using the shovel on an unplowed tile.
     */
    DIG_TILE_NOTHING("The tile is not plowed! There is nothing to do!"),

    /**
     * Message for using the shovel on a tile with a crop.
     */
    DIG_CROP_REMOVED("The crop was removed!"),

    /**
     * Message for attempting to use the shovel on a tile with a rock.
     */
    DIG_HAS_ROCK("The tile is occupied by a rock!");

    private String message;

    /**
     * Stores a string message for feedback.
     * @param message   The string message.
     */
    private TileActionReportMessages(String message) {
        this.message = message;
    }

    /**
     * Returns the string message feedback of the tile action.
     */
    @Override
    public String toString() {
        return message;
    }
}
