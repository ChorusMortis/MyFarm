package game.crop;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Holds the string names of each crop.
 */
public enum CropName {
    /**
     * String name of the turnip root crop. Not to be confused with the
     * turnips flower crop.
     */
    TURNIP("Turnip"),

    /**
     * String name for the carrot root crop.
     */
    CARROT("Carrot"),

    /**
     * String name for the potato root crop.
     */
    POTATO("Potato"),

    /**
     * String name for the rose flower crop.
     */
    ROSE("Rose"),

    /**
     * String name for the turnips flower crop. Not to be confused with the
     * turnip root crop.
     */
    TURNIPS("Turnips"),

    /**
     * String name for the sunflower flower crop.
     */
    SUNFLOWER("Sunflower"),

    /**
     * String name for the mango fruit tree crop.
     */
    MANGO("Mango"),

    /**
     * String name for the apple fruit tree crop.
     */
    APPLE("Apple");

    private final static Map<String, CropName> stringNameToEnum;

    private String stringName;

    static {
        Map<String, CropName> map = new HashMap<String, CropName>();
        for (CropName c : CropName.values()) {
            map.put(c.getStringName().toLowerCase(), c);
        }
        stringNameToEnum = Collections.unmodifiableMap(map);
    }

    /**
     * Creates a set string name for a crop.
     * @param stringName   The string name of the crop.
     */
    private CropName(String stringName) {
        this.stringName = stringName;
    }

    /**
     * Returns the CropName enum associated to the crop's string name.
     * @param name   The string name of the crop.
     * @return
     *    A CropName enum   if the string name is mapped to a CropName enum.
     *    null              otherwise.
     */
    public static CropName getFromString(String name) {
        return stringNameToEnum.get(name.toLowerCase());
    }

    /**
     * Returns the string name of the crop.
     */
    @Override
    public String toString() {
        return this.stringName;
    }

    public String getStringName() {
        return stringName;
    }
}
