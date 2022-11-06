package app;

import java.util.Scanner;

import game.crop.*;
import game.farm.*;
import game.player.*;
import game.tile.*;

import app.MenuOption.*;

/**
 * Executes and runs the main game.
 * @version   1.0
 */
public final class Application {
    private final static Scanner sc = new Scanner(System.in);
    private static Player player;
    private static boolean running = true;

    private Application() {
    }

    /**
     * Executes the game as long as the player wishes to continue.
     * @param args   No arguments are parsed or used.
     */
    public static void main(String[] args) {
        while (running) {
            running = playGame();
            System.out.println();
        }
        sc.close();
    }

    /**
     * The subroutine that actually runs the game and does all the work.
     * @return
     *    true    if the player wishes to restart the game and continue
     *            playing.
     *    false   otherwise.
     */
    public static boolean playGame() {
        initializeGame();
        boolean restartGame = false;

        boolean keepPlaying = true;
        printGameState();
        while (keepPlaying) {
            printMainMenu();
            System.out.println();
            String action = getStringInput("Select action: ");
            System.out.println();

            MainMenuOption option = MainMenuOption.getFromSelector(action);
            if (option == null) {
                System.out.println("ERROR: Invalid input!");
            } else {
                keepPlaying = doMainMenuOption(option);
            }
            System.out.println();

            if (keepPlaying) {
                printGameState();
                keepPlaying = checkGameEndConditions();
            }

            if (!keepPlaying) {
                restartGame = askYesOrNo("Do you want to restart the game instead of exiting?");
            }
        }

        return restartGame;
    }

    /**
     * Initializes the game's (specifically the player's) initial data.
     */
    public static void initializeGame() {
        double defaultObjectCoins = 9999.0;
        int defaultLevel = 25;
        double defaultExperience = 2500.0;
        player = new Player(FarmerType.DEFAULT, defaultObjectCoins, defaultLevel, defaultExperience);
    }

    /**
     * Checks the game's ending conditions and returns a Boolean depending on
     * whether the game should end or not.
     * @return
     *    true    if at least one game ending condition is met.
     *    false   otherwise.
     */
    public static boolean checkGameEndConditions() {
        boolean keepPlaying = true;
        String message = null;

        // TODO: remove/modify this in MCO2
        if (getCrop() != null && getCrop().isWithered() &&
            player.getObjectCoins() < TileActionData.DIG.getMoneyCost()) {
                keepPlaying = false;
                message = "Uh-oh! You have a withered crop and can't afford to dig it up!";
        }

        // TODO: in MCO2, check every tile if it's empty AND check if each tile has a withered crop
        if (getCrop() == null &&
            player.getObjectCoins() < CropData.getLowestBaseSeedCost() - getFarmer().getSeedCostReduction()) {
                keepPlaying = false;
                message = "Uh-oh! You have no growing crops and you can't buy anything to plant!";   
            }

        if (message != null) {
            System.out.println(message);
        }

        return keepPlaying;
    }

    /**
     * Prints the game's state. This includes the player's stats and the state
     * of the tile(s), as well as the planted crop(s), if any.
     */
    public static void printGameState() {
        System.out.println("Player Stats");
        player.printState();
        System.out.println("Tile Info");
        getTile().printState();
        System.out.println();
    }

    /**
     * Prints the main menu, specifically the options' selectors, names, and
     * description.
     */
    public static void printMainMenu() {
        System.out.println("Main Menu\n");
        for (MainMenuOption o : MainMenuOption.values()) {
            System.out.format("[%s] %s :: %s\n", o.getSelector(), o.getName(), o.getDescription());
        }
    }

    /**
     * Executes the given main menu option.
     * @param option   The main menu option to be executed.
     * @return
     *    true    if the player stays on the menu (does not exit).
     *    false   otherwise.
     */
    public static boolean doMainMenuOption(MainMenuOption option) {
        boolean keepPlaying = true;

        switch (option) {
            case TILE_ACTIONS: openTileActionsMenu(); break;
            case NEXT_DAY: nextDay(); break;
            case REGISTER: openRegisterMenu(); break;
            case EXIT: keepPlaying = false; break;
        }

        return keepPlaying;
    }

    /**
     * Prints the tile actions menu, specifically the options' selectors,
     * names, and description.
     */
    public static void printTileActionsMenu() {
        System.out.println("Tile Actions\n");
        for (TileActionOption o : TileActionOption.values()) {
            System.out.format("[%s] %s :: %s\n", o.getSelector(), o.getName(), o.getDescription());
        }
    }

    /**
     * Opens the tile actions (sub)menu and executes what the player wants to
     * do to the tile(s).
     */
    public static void openTileActionsMenu() {
        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printTileActionsMenu();
            System.out.println();
            String action = getStringInput("Select action: ");
            System.out.println();

            TileActionOption option = TileActionOption.getFromSelector(action);
            if (option == null) {
                System.out.println("ERROR: Invalid input!");
            } else {
                stayOnMenu = doTileActionOption(option);
            }
            System.out.println();
        }

        System.out.println("Exiting the Tile Actions menu.");
    }

    /**
     * Executes the tile action that the player wants to do on the tile(s).
     * @param option   The action to be executed.
     * @return
     *    true    if the player does not exit the menu.
     *    false   otherwise.
     */
    public static boolean doTileActionOption(TileActionOption option) {
        boolean stayOnMenu = true;

        switch (option) {
            case PLOW: {
                // no preconditions
                TileActionReport r = getTile().plow();
                if (r.isSuccess()) {
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesPlowed();
                }
                System.out.println(r);
                break;
            }

            case PLANT: {
                if (getTile().isPlowed()) {
                    Crop crop = openBuyCropMenu();
                    if (crop != null) {
                        System.out.println();
                        double seedCostReduction = player.getFarmer().getSeedCostReduction();
                        TileActionReport r = getTile().plant(crop, player.getObjectCoins(), seedCostReduction);
                        if (r.isSuccess()) {
                            player.deductMoney(crop.getBaseSeedCost() - seedCostReduction);
                            player.addXP(r.getExpGained());
                            player.getStats().addTimesPlanted();
                        }
                        System.out.println(r);
                    }
                } else {
                    System.out.println("ERROR: The tile is not plowed yet!");
                }
                break;
            }
            
            case HARVEST: {
                if (getCrop() != null && getCrop().isHarvestable()) {
                    double bonusEarnings = getFarmer().getBonusEarnings();
                    HarvestCropReport r = getTile().harvest(bonusEarnings);
                    // harvests are always successful so no need for conditions
                    player.addMoney(r.getProfit());
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesHarvested();
                    System.out.println(r);
                } else {
                    System.out.println("ERROR: There is either no planted crop or the crop is not harvestable!");
                }
                break;
            }

            case WATER: {
                if (getCrop() != null && !getCrop().isWithered()) {
                    TileActionReport r = getTile().water();
                    // watering a crop is always successful (though it has no effect if crop's water is at its limit)
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesWatered();
                    System.out.println(r);   
                } else {
                    System.out.println("ERROR: There is either no planted crop or the crop is withered!");
                }
                break;
            }

            case FERTILIZE: {
                if (getCrop() != null && !getCrop().isWithered()) {
                    TileActionReport r = getTile().fertilize(player.getObjectCoins());
                    if (r.isSuccess()) {
                        // money is not deducted in fertilize() method so it must be deducted here
                        player.deductMoney(TileActionData.FERTILIZE.getMoneyCost());
                        player.addXP(r.getExpGained());
                        player.getStats().addTimesFertilized();
                    }
                    System.out.println(r);
                } else {
                    System.out.println("ERROR: There is either no crop or the crop is withered!");
                }
                break;
            }

            case DIG: {
                // no preconditions
                TileActionReport r = getTile().dig(player.getObjectCoins());
                // money may sometimes need to be deducted from player even though the action "failed"
                if (r.getExpGained() != 0) {
                    player.deductMoney(TileActionData.DIG.getMoneyCost());
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesDug();
                }
                System.out.println(r);
                break;
            }

            case EXIT: {
                stayOnMenu = false;
                break;
            }
        }

        return stayOnMenu;
    }

    /**
     * Prints the buy crop menu, specifically each buyable crop's stats.
     */
    public static void printBuyCropMenu() {
        System.out.println("Buy Crop\n");
        for (CropData c : CropData.values()) {
            var s = "Name: " + c.getName().getStringName() + "\n"
                  + "Type: " + c.getType().getStringName() + "\n"
                  + "Harvest Time (in days): " + c.getHarvestAge() + "\n"
                  + "Needed Water (Bonus Limit): " + c.getNeededWater() + "(" +
                    (c.getWaterLimit() + getFarmer().getWaterLimitIncrease()) + ")\n"
                  + "Needed Fertilizer (Bonus Limit): " + c.getNeededFertilizer() + "(" +
                    (c.getFertilizerLimit() + getFarmer().getFertilizerLimitIncrease()) + ")\n"
                  + "Yield: " + (c.getMinYield() == c.getMaxYield()
                                ? c.getMinYield()
                                : c.getMinYield() + "-" + c.getMaxYield()) + "\n"
                  + "Seed Cost: " + (c.getBaseSeedCost() - getFarmer().getSeedCostReduction()) + "\n"
                  + "Sell Price (per piece): " + (c.getBaseSellPrice() + getFarmer().getBonusEarnings()) + "\n"
                  + "XP Yield: " + c.getExpYield() + "\n";

            System.out.println(s);
        }
    }

    /**
     * Opens the buy crop menu, where the player can buy or not buy a crop.
     * @return
     *    A Crop   if the player buys a crop.
     *    null     otherwise.
     */
    public static Crop openBuyCropMenu() {
        Crop crop = null;

        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printBuyCropMenu();
            System.out.println("Enter the name of the crop you want to buy and plant.");
            String input = getStringInput("Alternatively, enter \"Exit\" to exit the menu: ");
            System.out.println();
            if (input.compareToIgnoreCase("Exit") == 0) {
                stayOnMenu = false;
            } else {
                CropData result = CropData.getFromCropName(CropName.getFromString(input));
                if (result != null) {
                    crop = new Crop(result);
                    crop.setWaterLimit(crop.getWaterLimit() + getFarmer().getWaterLimitIncrease());
                    crop.setFertilizerLimit(crop.getFertilizerLimit() + getFarmer().getFertilizerLimitIncrease());
                    stayOnMenu = false;
                } else {
                    System.out.println("ERROR: Crop not found!\n");
                }
            }
        }

        System.out.println("Exiting the Buy Crop menu.");

        return crop;
    }

    /**
     * Calls each tile's nextDay() method.
     * @see   Tile#nextDay()
     */
    public static void nextDay() {
        // TODO: in MCO2, replace with for each loop, calling each tile in farm lot's nextDay() method
        System.out.println("Moving on to the next day!\n");
        getTile().nextDay();
    }

    /**
     * Prints the registration menu, specifically each farmer rank/type's
     * stats.
     */
    public static void printRegistrationMenu() {
        System.out.println("Register\n");
        for (FarmerType f : FarmerType.values()) {
            var s = "Name: " + f.getStringName() + "\n"
                  + "Fee: " + f.getRegFee() + "\n"
                  + "Level Requirement: " + f.getLevelReq() + "\n"
                  + "Bonus Earnings: " + f.getBonusEarnings() + "\n"
                  + "Seed Cost Reduction: " + f.getSeedCostReduction() + "\n"
                  + "Water Limit Increase: " + f.getWaterLimitIncrease() + "\n"
                  + "Fertilizer Limit Increase: " + f.getFertilizerLimitIncrease() + "\n";
            
            System.out.println(s);
        }
    }

    /**
     * Opens the registration menu, where the player can register for a new
     * farmer type/rank.
     */
    public static void openRegisterMenu() {
        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printRegistrationMenu();
            System.out.println("Enter the name of the rank you would like to register for.");
            String input = getStringInput("Alternatively, enter \"Exit\" to go back to the previous menu: ");
            System.out.println();
            if (input.compareToIgnoreCase("Exit") == 0) {
                stayOnMenu = false;
            } else {
                FarmerType rank = FarmerType.getFromStringName(input);
                if (rank != null) {
                    RankRegistrationReport r = player.registerRank(rank);
                    if (r.isSuccess()) {
                        player.deductMoney(r.getCost());
                        int waterLimitIncrease = r.getNewWaterLimitIncrease() - getFarmer().getWaterLimitIncrease();
                        int fertilizerLimitIncrease = r.getNewFertilizerLimitIncrease()
                                                      - getFarmer().getFertilizerLimitIncrease();
                        player.setFarmer(new Farmer(r.getNewRank()));
                        // TODO: for MCO2, call this for every tile in the farm lot
                        getTile().updateCropStats(waterLimitIncrease, fertilizerLimitIncrease);
                    }
                    System.out.println(r);
                } else {
                    System.out.println("Rank not found!\n");
                }
            }
        }
    }

    /**
     * Displays a prompt and asks the player to enter a string.
     * @param prompt   Text to display before asking the player to enter a
     *                 string.
     * @return   The string entered by the user.
     */
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    /**
     * Displays a prompt and asks the player to enter "yes" or "no". Returns
     * the appropriate Boolean value.
     * @param prompt   Text to display before asking the player to enter "yes"
     *                 or "no".
     * @return
     *    true    if the player enters "yes".
     *    false   otherwise.
     */
    public static boolean askYesOrNo(String prompt) {
        boolean retval = false;

        boolean inputIsValid = false;
        while (!inputIsValid) {
            System.out.print(prompt + " (y/N): ");
            String response = sc.nextLine().toLowerCase();
            switch (response) {
                case "y": case "yes":
                    retval = true;
                    inputIsValid = true;
                    break;
                case "n": case "no":
                    retval = false;
                    inputIsValid = true;
                    break;
            }
        }

        return retval;
    }

    /**
     * Convenience method for getting the farm lot's tiles.
     * @return   The farm lot's tiles.
     */
    public static Tile getTile() {
        return player.getFarmLot().getTiles();
    }

    /**
     * Convenience method for getting the tile's planted crop.
     * @return   The tile's planted crop.
     */
    public static Crop getCrop() {
        return player.getFarmLot().getTiles().getPlantedCrop();
    }

    /**
     * Convenience method for getting the player's farmer instance.
     * @return   The player's farmer instance.
     */
    public static Farmer getFarmer() {
        return player.getFarmer();
    }
}
