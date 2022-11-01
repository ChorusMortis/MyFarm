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
 * 2. TODO: Display game status (info about tile, player stats, etc.) to player.
 * 3. TODO: Fix broken logic and bugs, especially in calculations and where null return values are possible.
 * 4. TODO: Apply stat changes after registration to calculations and newly created Crops.
 * 4. TODO: Improve prompts on menus and spacing in design.
 * 5. TODO: Fix getter and setter logic. Remove any unnecessary methods.
 * 6. TODO: Optimize and clean up code.
 * 7. TODO: Document code.
 */
public final class Application {
    private static Player player;
    private static boolean running = true;
    private final static Scanner sc = new Scanner(System.in);

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

        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printMainMenu();
            String action = getStringInput("Enter input: ");

            if (action.compareToIgnoreCase(MainMenuOption.TILE_ACTIONS.getSelector()) == 0)  {
                openTileActionsMenu();
            } else if (action.compareToIgnoreCase(MainMenuOption.NEXT_DAY.getSelector()) == 0) {
                nextDay();
            } else if (action.compareToIgnoreCase(MainMenuOption.REGISTER.getSelector()) == 0) {
                openRegisterMenu();
            } else if (action.compareToIgnoreCase(MainMenuOption.EXIT.getSelector()) == 0) {
                restartGame = askYesOrNo("Do you want to restart the game instead of exiting?");
                stayOnMenu = false;
            } else {
                System.out.println("Invalid input!");
            }
        }

        return restartGame;
    }

    public static void initializeGame() {
        player = new Player(FarmerType.DEFAULT);
    }

    public static void printMainMenu() {
        System.out.println("Main Menu\n");
        for (MainMenuOption o : MainMenuOption.values()) {
            System.out.format("[%s] %s :: %s\n".indent(3), o.getSelector(), o.getName(), o.getDescription());
        }
    }

    public static void openTileActionsMenu() {
        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printTileActionsMenu();
            String action = getStringInput("Enter input: ");

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
                        double seedCostReduction = player.getFarmer().getSeedCostReduction();
                        TileActionReport r = getTile().plant(crop, player.getObjectCoins(), seedCostReduction);

                        if (r.isSuccess()) {
                            player.deductMoney(crop.getBaseSeedCost() - seedCostReduction);
                            player.addXP(r.getExpGained());
                            player.getStats().addTimesPlanted();
                        }

                        System.out.println(r);
                    }
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
                    System.out.println("There is either no planted crop or the crop is not harvestable!");
                }
            } else if (action.compareToIgnoreCase(TileActionOption.WATER.getSelector()) == 0) {
                if (getCrop() != null && !getCrop().isWithered()) {
                    TileActionReport r = getTile().water();

                    // watering a crop is always successful (though it has no effect if crop's water is at its limit)
                    player.addXP(r.getExpGained());
                    player.getStats().addTimesWatered();

                    System.out.println(r);   
                } else {
                    System.out.println("There is either no planted crop or the crop is withered!");
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
                    System.out.println("There is either no crop or the crop is withered!");
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
                System.out.println("Invalid input!");
            }
        }
    }

    public static void printTileActionsMenu() {
        System.out.println("Tile Actions\n");
        for (TileActionOption o : TileActionOption.values()) {
            System.out.format("[%s] %s :: %s\n".indent(3), o.getSelector(), o.getName(), o.getDescription());
        }
    }

    public static Crop openBuyCropMenu() {
        Crop crop = null;

        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printBuyCropMenu();
            System.out.println("Enter the name of the crop you want to buy and plant.");
            String input = getStringInput("Alternatively, enter \"Exit\" to exit the menu: ");
            if (input.compareToIgnoreCase("Exit") == 0) {
                stayOnMenu = false;
            } else {
                CropData result = CropData.getFromCropName(CropName.getFromString(input));
                if (result != null) {
                    crop = new Crop(result);
                    stayOnMenu = false;
                } else {
                    System.out.println("Crop not found!");
                }
            }
        }

        return crop;
    }

    public static void printBuyCropMenu() {
        // TODO: improve formatting of this
        System.out.println("Buy Crop\n");
        System.out.format("%s | %s | %s | %s | %s | %s | %s | %s | %s\n",
                          "Name", "Type", "Harvest Time (in days)", "Needed Water (Bonus Limit)",
                          "Needed Fertilizer (Bonus Limit)", "Products Produced", "Seed Cost",
                          "Base Sell Price Per Piece", "XP Yield");
        for (CropData c : CropData.values()) {
            System.out.format("%s | %s | %d | %d(%d) | %d(%d) | %d-%d | %.2f | %.2f | %.2f\n",
                              c.getName().getStringName(), c.getType().getStringName(), c.getHarvestAge(),
                              c.getNeededWater(), c.getWaterLimit(), c.getNeededFertilizer(), c.getFertilizerLimit(),
                              c.getMinYield(), c.getMaxYield(), c.getBaseSeedCost(), c.getBaseSellPrice(),
                              c.getExpYield());
        }
    }

    public static void openRegisterMenu() {
        boolean stayOnMenu = true;
        while (stayOnMenu) {
            printRegistrationMenu();
            System.out.println("Enter the name of the rank you would like to register for.");
            String input = getStringInput("Alternatively, enter \"Exit\" to go back to the previous menu: ");
            if (input.compareToIgnoreCase("Exit") == 0) {
                stayOnMenu = false;
            } else {
                FarmerType rank = FarmerType.getFromStringName(input);
                if (rank != null) {
                    RankRegistrationReport r = player.registerRank(rank);
                    if (r.isSuccess()) {
                        player.deductMoney(r.getCost());
                        player.setFarmer(new Farmer(r.getNewRank()));
                        int waterLimitIncrease = r.getNewWaterLimitIncrease();
                        int fertilizerLimitIncrease = r.getNewFertilizerLimitIncrease();
                        // TODO: for MCO2, call this for every tile in the farm lot
                        getTile().updateCropStats(waterLimitIncrease, fertilizerLimitIncrease);
                    }
                    System.out.println(r);
                } else {
                    System.out.println("Rank not found!");
                }
            }
        }
    }

    public static void printRegistrationMenu() {
        // TODO: improve formatting of this
        System.out.println("Register\n");
        System.out.format("%s | %s | %s | %s | %s | %s | %s\n",
                          "Name", "Fee", "Level Requirement", "Bonus Earnings", "Seed Cost Reduction",
                          "Water Limit Increase", "Fertilizer Limit Increase");
        for (FarmerType f : FarmerType.values()) {
            System.out.format("%s | %.2f | %d | %.2f | %.2f | %d | %d\n",
                              f.getStringName(), f.getRegFee(), f.getLevelReq(), f.getBonusEarnings(),
                              f.getSeedCostReduction(), f.getWaterLimitIncrease(), f.getFertilizerLimitIncrease());
        }
    }

    public static void nextDay() {
        // TODO: in MCO2, replace with for each loop, calling each tile in farm lot's nextDay() method
        getTile().nextDay();
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
