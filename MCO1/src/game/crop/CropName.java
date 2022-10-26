package game.crop;

public enum CropName {
    TURNIP("Turnip"),
    CARROT("Carrot"),
    POTATO("Potato"),
    ROSE("Rose"),
    TURNIPS("Turnips"),
    SUNFLOWER("Sunflower"),
    MANGO("Mango"),
    APPLE("Apple");

    String stringName;

    private CropName(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    @Override
    public String toString() {
        return this.stringName;
    }
}
