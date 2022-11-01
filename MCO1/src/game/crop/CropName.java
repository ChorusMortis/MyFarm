package game.crop;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public enum CropName {
    TURNIP("Turnip"),
    CARROT("Carrot"),
    POTATO("Potato"),
    ROSE("Rose"),
    TURNIPS("Turnips"),
    SUNFLOWER("Sunflower"),
    MANGO("Mango"),
    APPLE("Apple");

    private String stringName;

    private final static Map<String, CropName> ENUM_MAP;

    private CropName(String stringName) {
        this.stringName = stringName;
    }

    static {
        Map<String, CropName> map = new HashMap<String, CropName>();
        for (CropName c : CropName.values()) {
            map.put(c.getStringName().toLowerCase(), c);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CropName getFromString(String name) {
        return ENUM_MAP.get(name);
    }

    public String getStringName() {
        return stringName;
    }

    @Override
    public String toString() {
        return this.stringName;
    }
}
