package game.crop;

public enum CropType {
    ROOT_CROP("Root Crop"),
    FLOWER("Flower"),
    FRUIT_TREE("Fruit Tree");

    private String stringName;

    private CropType(String stringName) {
        this.stringName = stringName;
    }

    @Override
    public String toString() {
        return this.stringName;
    }
    
    public String getStringName() {
        return stringName;
    }
}
