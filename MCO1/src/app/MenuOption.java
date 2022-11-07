package app;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import game.tile.TileActionData;

/**
 * Stores data for the application's main menus.
 */
public class MenuOption {
    /**
     * Stores data for main menu options.
     */
    public enum MainMenuOption {
        /**
         * Option for working on the tile(s).
         */
        TILE_ACTIONS("T", "Tile Actions", "Do some work on a tile."),

        /**
         * Option for moving on to the next day.
         */
        NEXT_DAY("N", "Next Day", "Let crops grow and move on to the next day."),

        /**
         * Option for registering for a new rank/type.
         */
        REGISTER("R", "Register", "Register for a rank for additional benefits."),

        /**
         * Option for exiting the game.
         */
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
        
        /**
         * Creates an option for the main menu.
         * @param selector      What the player can use to select the option.
         * @param name          The name of the action/option.
         * @param description   What the option does.
         */
        private MainMenuOption(String selector, String name, String description) {
            this.selector = selector;
            this.name = name;
            this.description = description;
        }

        /**
         * Returns the option enum associated to the selector.
         * @param selector   The selector of the option.
         * @return
         *    A MainMenuOption enum   if the selector is mapped to an option.
         *    null                    otherwise.
         */
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

    /**
     * Stores data for tile action options.
     */
    public enum TileActionOption {
        /**
         * Option for plowing a tile.
         */
        PLOW("P", "Plow Tile", "Converts an unplowed tile to a plowed tile."),

        /**
         * Option for planting a crop on a tile.
         */
        PLANT("C", "Plant Crop", "Plants a crop on a tile."),

        /**
         * Option for harvesting a planted crop.
         */
        HARVEST("H", "Harvest Crop", "Harvests a crop on a tile and sells it."),

        /**
         * Option for watering a planted crop.
         */
        WATER("W", "Water Crop", "Waters a crop. Increases its current water amount."),

        /**
         * Option for fertilizing a planted crop.
         */
        FERTILIZE("F", "Fertilize Crop",
                "Fertilizes a crop. Increases its current fertilizer amount. Costs "
                        + TileActionData.FERTILIZE.getMoneyCost() + " Objectcoins."),

        /**
         * Option for digging a planted crop. Has other effects, depending on
         * the tile's state.
         */
        DIG("D", "Dig",
                "Removes a withered plant from a tile. Costs " + TileActionData.DIG.getMoneyCost() + " Objectcoins."),

        /**
         * Option for exiting the menu.
         */
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

        /**
         * Creates a tile action option for tile actions menu.
         * @param selector      What the player can use to select the option.
         * @param name          The name of the action/option.
         * @param description   What the option does.
         */
        private TileActionOption(String selector, String name, String description) {
            this.selector = selector;
            this.name = name;
            this.description = description;
        }

        /**
         * Returns the option enum associated to the selector.
         * @param selector   The selector of the option.
         * @return
         *    A TileActionOption enum   if the selector is mapped to an
         *                              option.
         *    null                      otherwise.
         */
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
