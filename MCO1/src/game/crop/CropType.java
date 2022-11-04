package game.crop;

/**
 * Holds the string name of crop types.
 */
public enum CropType {
    /**
     * String name for the root crop type.
     */
    ROOT_CROP("Root Crop"),

    /**
     * String name for the flower crop type.
     */
    FLOWER("Flower"),

    /**
     * String name for the fruit tree crop type.
     */
    FRUIT_TREE("Fruit Tree");

    private String stringName;

    /**
     * Creates a set string name for a crop type.
     * @param stringName   The string name of the crop type.
     */
    private CropType(String stringName) {
        this.stringName = stringName;
    }

    /**
     * Returns the string name of the crop type.
     */
    @Override
    public String toString() {
        return this.stringName;
    }
    
    public String getStringName() {
        return stringName;
    }
}
