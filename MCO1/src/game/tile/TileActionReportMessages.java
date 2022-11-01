package game.tile;

public enum TileActionReportMessages {
    PLOW_SUCCESS("The tile was successfully plowed!"),
    PLOW_IS_PLOWED("The tile is already plowed!"),
    PLOW_HAS_CROP("The tile is occupied by a crop!"),
    PLOW_HAS_ROCK("The tile is occupied by a rock!"),

    PLANT_SUCCESS("The crop was successfully planted on the tile!"),
    PLANT_NO_MONEY("You have insufficient money!"),
    PLANT_HAS_CROP("The tile is occupied by a crop!"),
    PLANT_HAS_ROCK("The tile is occupied by a rock!"),
    PLANT_NO_ADJ_SPACE("You cannot plant this crop here because some of the surrounding tiles are occupied!"),

    WATER_SUCCESS("The crop was successfully watered!"),
    WATER_HAS_ROCK("The tile is occupied by a rock!"),

    FERTILIZE_SUCCESS("The crop was successfully fertilized!"),
    FERTILIZE_NO_MONEY("You have insufficient money!"),
    FERTILIZE_HAS_ROCK("The tile is occupied by a rock!"),

    MINE_SUCCESS("The rock was successfully removed!"),
    MINE_NO_MONEY("You have insufficient money!"),

    DIG_NO_MONEY("You have insufficient money!"),
    DIG_TILE_UNPLOWED("The tile was unplowed!"),
    DIG_TILE_NOTHING("The tile is not plowed! There is nothing to do!"),
    DIG_CROP_REMOVED("The crop was removed!"),
    DIG_HAS_ROCK("The tile is occupied by a rock!");

    private String message;

    private TileActionReportMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
