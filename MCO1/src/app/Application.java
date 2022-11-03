package app;

import java.util.Scanner;

import game.crop.*;
import game.farm.*;
import game.player.*;
import game.tile.*;

import app.MenuOption.*;

/**
 * TODO List: (Listed in descending order by priority)
 * 1. Implement the rank registration feature.
 * 2. Display game status (info about tile, player stats, etc.) to player.
 * 3. Apply stat changes after registration to calculations and newly created Crops.
 * 4. Implement game-ending conditions.
 * 5. Improve prompts on menus and spacing in design.
 * 6. Fix getter and setter logic. Remove any unnecessary methods.
 * 7. TODO: Optimize and clean up code.
 * 8. TODO: Document code.
 */
public final class Application {
    private final static Scanner sc = new Scanner(System.in);
    private static Player player;
    private static boolean running = true;

    private Application() {
    }

    public static void main(String[] args) {
        while (running) {
            running = playGame();
        }
    }

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

            if (action.compareToIgnoreCase(MainMenuOption.TILE_ACTIONS.getSelector()) == 0)  {
                openTileActionsMenu();
            } else if (action.compareToIgnoreCase(MainMenuOption.NEXT_DAY.getSelector()) == 0) {
                nextDay();
            } else if (action.compareToIgnoreCase(MainMenuOption.REGISTER.getSelector()) == 0) {
                openRegisterMenu();
            } else if (action.compareToIgnoreCase(MainMenuOption.EXIT.getSelector()) == 0) {
                restartGame = askYesOrNo("Do you want to restart the game instead of exiting?");
                keepPlaying = false;
            } else {
                System.out.println("ERROR: Invalid input!");
            }
            System.out.println();

            if (keepPlaying) {
                printGameState();
                keepPlaying = checkGameEndConditions();
                if (!keepPlaying) {
                    restartGame = askYesOrNo("Do you want to restart the game instead of exiting?");
                }
            }
        }

        return restartGame;
    }

    public static void initializeGame() {
        double defaultObjectCoins = 100.0;
        int defaultLevel = 0;
        double defaultExperience = 0.0;
        player = new Player(FarmerType.DEFAULT, defaultObjectCoins, defaultLevel, defaultExperience);
    }

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
        if (getCrop() == null && player.getObjectCoins() < CropData.getLowestBaseSeedCost()) {
            keepPlaying = false;
            message = "Uh-oh! You have no growing crops and you can't buy anything to plant!";
        }

        if (message != null) {
            System.out.println(message);
        }

        return keepPlaying;
    }

    public static void printGameState() {
        System.out.println("Player Stats");
        player.printState();
        System.out.println("Tile Info");
        getTile().printState();
        System.out.println();
    }

    public static void printMainMenu() {
        System.out.println("Main Menu\n");
        for (MainMenuOption o : MainMenuOption.values()) {
            System.out.format("[%s] %s :: %s\n", o.getSelector(), o.getName(), o.getDescription());
        }
    }

    public static void printTileActionsMenu() {
        System.out.println("Tile Actions\n");
        for (TileActionOption o : TileActionOption.values()) {
            System.out.format("[%s] %s :: %s\n", o.getSelector(), o.getName(), o.getDescription());
        }
    }

    public static void openTileActionsMenu() {
        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printTileActionsMenu();
            System.out.println();
            String action = getStringInput("Select action: ");
            System.out.println();

            if (action.compareToIgnoreCase(TileActionOption.PLOW.getSelector()) == 0) {
                // no preconditions
                TileActionReport r = getTile().plow();
                if (r.isSuccess()) {
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesPlowed();
                }
                System.out.println(r);
            } else if (action.compareToIgnoreCase(TileActionOption.PLANT.getSelector()) == 0) {
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
            } else if (action.compareToIgnoreCase(TileActionOption.HARVEST.getSelector()) == 0) {
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
            } else if (action.compareToIgnoreCase(TileActionOption.WATER.getSelector()) == 0) {
                if (getCrop() != null && !getCrop().isWithered()) {
                    TileActionReport r = getTile().water();

                    // watering a crop is always successful (though it has no effect if crop's water is at its limit)
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesWatered();

                    System.out.println(r);   
                } else {
                    System.out.println("ERROR: There is either no planted crop or the crop is withered!");
                }
            } else if (action.compareToIgnoreCase(TileActionOption.FERTILIZE.getSelector()) == 0) {
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
            } else if (action.compareToIgnoreCase(TileActionOption.DIG.getSelector()) == 0) {
                // no preconditions
                TileActionReport r = getTile().dig(player.getObjectCoins());

                // money may sometimes need to be deducted from player even though the action "failed"
                if (r.getExpGained() != 0) {
                    player.deductMoney(TileActionData.DIG.getMoneyCost());
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesDug();
                }

                System.out.println(r);
            } else if (action.compareToIgnoreCase(TileActionOption.EXIT.getSelector()) == 0) {
                stayOnMenu = false;
            } else {
                System.out.println("ERROR: Invalid input!");
            }
            System.out.println();
        }

        System.out.println("Exiting the Tile Actions menu.");
    }

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

    public static void nextDay() {
        // TODO: in MCO2, replace with for each loop, calling each tile in farm lot's nextDay() method
        System.out.println("Moving on to the next day!\n");
        getTile().nextDay();
    }

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
                        player.setFarmer(new Farmer(r.getNewRank()));
                        int waterLimitIncrease = r.getNewWaterLimitIncrease() - getFarmer().getWaterLimitIncrease();
                        int fertilizerLimitIncrease = r.getNewFertilizerLimitIncrease()
                                                      - getFarmer().getFertilizerLimitIncrease();
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

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

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
    
    public static Tile getTile() {
        return player.getFarmLot().getTiles();
    }
    public static Crop getCrop() {
        return player.getFarmLot().getTiles().getPlantedCrop();
    }
    public static Farmer getFarmer() {
        return player.getFarmer();
    }
}
