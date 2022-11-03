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

    private final static Map<String, CropName> stringNameToEnum;

    private String stringName;

    static {
        Map<String, CropName> map = new HashMap<String, CropName>();
        for (CropName c : CropName.values()) {
            map.put(c.getStringName().toLowerCase(), c);
        }
        stringNameToEnum = Collections.unmodifiableMap(map);
    }

    private CropName(String stringName) {
        this.stringName = stringName;
    }

    public static CropName getFromString(String name) {
        return stringNameToEnum.get(name.toLowerCase());
    }
    
    @Override
    public String toString() {
        return this.stringName;
    }

    public String getStringName() {
        return stringName;
    }
}
