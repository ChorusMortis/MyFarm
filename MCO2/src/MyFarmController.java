import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Represents the controller part of the game which controls the data flow
 * and updates the GUI.
 */
public class MyFarmController implements ActionListener {
    private MyFarm myFarm;
    private MyFarmGUI gui;
    private Map<JButton, Tile> buttonToTile;
    private JButton selectedTileButton;
    private Tile selectedTile;

    /**
     * Creates and instantiates the controller given an instance of the model
     * and the view.
     * @param myFarm   The model part of the game.
     * @param gui      The view part of the game.
     */
    public MyFarmController(MyFarm myFarm, MyFarmGUI gui) {
        this.myFarm = myFarm;
        this.gui = gui;
        
        mapButtonsToTiles();
        gui.updateAllTiles(buttonToTile);
        updateInfoPanel(null);
        gui.setActionsPanelListener(this);
        gui.setTilesPanelListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Tile")) {
            selectedTileButton = (JButton)e.getSource();
            selectedTile = buttonToTile.get(e.getSource());
            updateTileInfo();
            return;
        }

        ActionReport report = null;
        switch (actionCommand) {
            case "Plow" -> report = plowButton();
            case "Plant" -> report = plantButton();
            case "Harvest" -> report = harvestButton();
            case "Water" -> report = waterButton();
            case "Fertilize" -> report = fertilizeButton();
            case "Dig" -> report = digButton();
            case "Mine" -> report = mineButton();
            case "Next Day" -> report = nextDayButton();
            case "Register" -> report = registerButton();
            case "Exit" -> exitButton();
        }
        
        updateInfoPanel(report);
        if (!myFarm.shouldGameContinue()) {
            String reasonForGameEnd = myFarm.getReasonForGameEnd();
            JOptionPane.showMessageDialog(gui, reasonForGameEnd, "Game has ended", JOptionPane.WARNING_MESSAGE);
            gui.exitProgram();
        }
    }

    /**
     * Maps the tile buttons to their corresponding tiles.
     */
    private void mapButtonsToTiles() {
        Map<JButton, Tile> map = new HashMap<JButton, Tile>();
        ArrayList<JButton> buttons = gui.getTilePanelButtons();
        Tile[][] tiles = myFarm.getTiles();
        int k = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                map.put(buttons.get(k), tiles[i][j]);
                k++;
            }
        }
        buttonToTile = Collections.unmodifiableMap(map);
    }

    /**
     * Invoked when the player clicks on the Plow button.
     * Attempts to plow the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport plowButton() {
        ActionReport report = myFarm.plowTile(selectedTile);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Plant button.
     * Lets the player select a crop to plant on the tile. Afterwards,
     * it attempts to plant the crop on the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport plantButton() {
        Crop crop = gui.openBuyCropMenu(gui);
        ActionReport report = myFarm.plantCropOnTile(selectedTile, crop);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Harvest button.
     * Attempts to harvest the crop on the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport harvestButton() {
        ActionReport report = myFarm.harvestCropFromTile(selectedTile);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Water button.
     * Attempts to water the crop on the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport waterButton() {
        ActionReport report = myFarm.waterCropOnTile(selectedTile);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Fertilize button.
     * Attempts to fertilize the crop on the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport fertilizeButton() {
        ActionReport report = myFarm.fertilizeCropOnTile(selectedTile);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Dig button.
     * Attempts to dig whatever is on the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport digButton() {
        ActionReport report = myFarm.digSomethingOnTile(selectedTile);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Mine button.
     * Attempts to mine a rock on the tile and updates it visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport mineButton() {
        ActionReport report = myFarm.mineRockFromTile(selectedTile);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateTile(selectedTile, selectedTileButton);
        return report;
    }

    /**
     * Invoked when the player clicks on the Next Day button.
     * Lets the crops age, updates their state, and updates all the tiles
     * visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport nextDayButton() {
        ActionReport report = myFarm.moveOnToNextDay();
        gui.updateAllTiles(buttonToTile);
        return report;
    }

    /**
     * Invoked when the player clicks on the Register button.
     * Lets the player select a new farmer type/rank to register for.
     * Afterwards, it attempts to update the player's statistics using the
     * selected farmer type/rank. All existing crops on the farm have their
     * stats updated, and so all tiles are updated visually.
     * @return   A report containing feedback about the action.
     */
    private ActionReport registerButton() {
        Stats rank = gui.openRankRegistrationMenu(gui);
        ActionReport report = myFarm.registerForRank(rank);
        myFarm.updatePlayerStatsFromReport(report);
        gui.updateAllTiles(buttonToTile);
        return report;
    }

    /**
     * Invoked when the player clicks on the Exit button.
     * Calls the GUI's exit method which terminates the program.
     */
    private void exitButton() {
        gui.exitProgram();
    }

    /**
     * Updates the entire information panel at the bottom of the screen.
     * This panel includes the selected tile info box, the player stats box,
     * and the action report box.
     * @param report   A report containing feedback about the player's most
     *                 recent action.
     */
    private void updateInfoPanel(ActionReport report) {
        updateTileInfo();
        updatePlayerStats();
        updateActionReport(report);
    }

    /**
     * Updates the player stats information box in the information panel.
     */
    private void updatePlayerStats() {
        gui.updatePlayerStats(myFarm.getPlayerStats());
    }

    /**
     * Updates the selected tile information box in the information panel.
     */
    private void updateTileInfo() {
        gui.updateSelectedTileInfo(selectedTile);
    }

    /**
     * Updates the action report information box in the information panel.
     * @param report   A report containing feedback about the player's most
     *                 recent action.
     */
    private void updateActionReport(ActionReport report) {
        gui.updateActionReport(report);
    }
}
