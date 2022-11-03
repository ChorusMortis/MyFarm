package app;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class MenuOption {
    public enum MainMenuOption {
        TILE_ACTIONS("T", "Tile Actions", "Do some work on a tile."),
        NEXT_DAY("N", "Next Day", "Let crops grow and move on to the next day."),
        REGISTER("R", "Register", "Register for a rank for additional benefits."),
        EXIT("E", "Exit Game", "Quits the game.");

        private static final Map<String, MainMenuOption> selectorToMainMenuOption;

        private String selector;
        private String name;
        private String description;

        static {
            Map<String, MainMenuOption> map = new HashMap<String, MainMenuOption>();
            for (MainMenuOption o : MainMenuOption.values()) {
                map.put(o.getSelector().toLowerCase(), o);
            }
            selectorToMainMenuOption = Collections.unmodifiableMap(map);
        }

        private MainMenuOption(String selector, String name, String description) {
            this.selector = selector;
            this.name = name;
            this.description = description;
        }

        public static MainMenuOption getFromSelector(String selector) {
            return selectorToMainMenuOption.get(selector.toLowerCase());
        }

        public String getSelector() {
            return selector;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
    }

    public enum TileActionOption {
        PLOW("P", "Plow Tile", "Converts an unplowed tile to a plowed tile."),
        PLANT("C", "Plant Crop", "Plants a crop on a tile."),
        HARVEST("H", "Harvest Crop", "Harvests a crop on a tile and sells it."),
        WATER("W", "Water Crop", "Waters a crop. Increases its current water amount."),
        FERTILIZE("F", "Fertilize Crop", "Fertilizes a crop. Increases its current fertilizer amount."),
        DIG("D", "Dig", "Removes a withered plant from a tile."),
        EXIT("E", "Exit", "Goes back to the previous menu."); // not really a tile action but is part of the menu
        // TODO: MINE("M", "Mine", "Removes a rock from a tile.");

        private static final Map<String, TileActionOption> selectorToTileActionOption;

        private String selector;
        private String name;
        private String description;

        static {
            Map<String, TileActionOption> map = new HashMap<String, TileActionOption>();
            for (TileActionOption o : TileActionOption.values()) {
                map.put(o.getSelector().toLowerCase(), o);
            }
            selectorToTileActionOption = Collections.unmodifiableMap(map);
        }

        private TileActionOption(String selector, String name, String description) {
            this.selector = selector;
            this.name = name;
            this.description = description;
        }

        public static TileActionOption getFromSelector(String selector) {
            return selectorToTileActionOption.get(selector.toLowerCase());
        }

        public String getSelector() {
            return selector;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
    }
}
