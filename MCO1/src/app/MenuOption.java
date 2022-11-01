package app;

public class MenuOption {
    public enum MainMenuOption {
        TILE_ACTIONS("T", "Tile Actions", "Do some work on a tile."),
        NEXT_DAY("N", "Next Day", "Let crops grow and move on to the next day."),
        REGISTER("R", "Register", "Register for a rank for additional benefits."),
        EXIT("E", "Exit Game", "Quits the game.");
    
        private String selector;
        private String name;
        private String description;

        private MainMenuOption(String selector, String name, String description) {
            this.selector = selector;
            this.name = name;
            this.description = description;
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
        // MINE("M", "Mine", "Removes a rock from a tile.");

        private String selector;
        private String name;
        private String description;
        
        private TileActionOption(String selector, String name, String description) {
            this.selector = selector;
            this.name = name;
            this.description = description;
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
