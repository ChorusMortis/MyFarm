import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Represents the view part of the game which handles the displaying of data
 * to the user whenever the user does something or when the model is updated.
 */
public class MyFarmGUI extends JFrame {
    public static final String MAIN_WINDOW_ICON_PATH = "./assets/application/main_window_icon.png";

    public static final Icon UNPLOWED_TILE = IconUtils.resize("./assets/tiles/unplowed_tile.png", 75, 75);
    public static final Icon PLOWED_TILE_EMPTY = IconUtils.resize("./assets/tiles/plowed_tile_empty.png", 75, 75);
    public static final Icon TILE_WITH_ROCK = IconUtils.resize("./assets/tiles/tile_with_rock.png", 75, 75);

    public static final Icon TURNIP_YOUNG = IconUtils.resize("./assets/crops/young/turnip_young.png", 75, 75);
    public static final Icon CARROT_YOUNG = IconUtils.resize("./assets/crops/young/carrot_young.png", 75, 75);
    public static final Icon POTATO_YOUNG = IconUtils.resize("./assets/crops/young/potato_young.png", 75, 75);
    public static final Icon ROSE_YOUNG = IconUtils.resize("./assets/crops/young/rose_young.png", 75, 75);
    public static final Icon TULIPS_YOUNG = IconUtils.resize("./assets/crops/young/tulips_young.png", 75, 75);
    public static final Icon SUNFLOWER_YOUNG = IconUtils.resize("./assets/crops/young/sunflower_young.png", 75, 75);
    public static final Icon MANGO_YOUNG = IconUtils.resize("./assets/crops/young/mango_young.png", 75, 75);
    public static final Icon APPLE_YOUNG = IconUtils.resize("./assets/crops/young/apple_young.png", 75, 75);
    
    public static final Icon TURNIP_GROWN = IconUtils.resize("./assets/crops/grown/turnip_grown.png", 75, 75);
    public static final Icon CARROT_GROWN = IconUtils.resize("./assets/crops/grown/carrot_grown.png", 75, 75);
    public static final Icon POTATO_GROWN = IconUtils.resize("./assets/crops/grown/potato_grown.png", 75, 75);
    public static final Icon ROSE_GROWN = IconUtils.resize("./assets/crops/grown/rose_grown.png", 75, 75);
    public static final Icon TULIPS_GROWN = IconUtils.resize("./assets/crops/grown/tulips_grown.png", 75, 75);
    public static final Icon SUNFLOWER_GROWN = IconUtils.resize("./assets/crops/grown/sunflower_grown.png", 75, 75);
    public static final Icon MANGO_GROWN = IconUtils.resize("./assets/crops/grown/mango_grown.png", 75, 75);
    public static final Icon APPLE_GROWN = IconUtils.resize("./assets/crops/grown/apple_grown.png", 75, 75);

    public static final Icon TURNIP_WITHERED = IconUtils.resize("./assets/crops/withered/turnip_withered.png", 75, 75);
    public static final Icon CARROT_WITHERED = IconUtils.resize("./assets/crops/withered/carrot_withered.png", 75, 75);
    public static final Icon POTATO_WITHERED = IconUtils.resize("./assets/crops/withered/potato_withered.png", 75, 75);
    public static final Icon ROSE_WITHERED = IconUtils.resize("./assets/crops/withered/rose_withered.png", 75, 75);
    public static final Icon TULIPS_WITHERED = IconUtils.resize("./assets/crops/withered/tulips_withered.png", 75, 75);
    public static final Icon SUNFLOWER_WITHERED = IconUtils.resize("./assets/crops/withered/sunflower_withered.png", 75, 75);
    public static final Icon MANGO_WITHERED = IconUtils.resize("./assets/crops/withered/mango_withered.png", 75, 75);
    public static final Icon APPLE_WITHERED = IconUtils.resize("./assets/crops/withered/apple_withered.png", 75, 75);

    public static final Icon PLOW_ICON = IconUtils.resize("./assets/actions/plow_icon.png", 50, 50);
    public static final Icon PLANT_ICON = IconUtils.resize("./assets/actions/plant_icon.png", 50, 50);
    public static final Icon HARVEST_ICON = IconUtils.resize("./assets/actions/harvest_icon.png", 50, 50);
    public static final Icon WATER_ICON = IconUtils.resize("./assets/actions/water_icon.png", 50, 50);
    public static final Icon FERTILIZE_ICON = IconUtils.resize("./assets/actions/fertilize_icon.png", 50, 50);
    public static final Icon DIG_ICON = IconUtils.resize("./assets/actions/dig_icon.png", 50, 50);
    public static final Icon MINE_ICON = IconUtils.resize("./assets/actions/mine_icon.png", 50, 50);
    public static final Icon NEXT_DAY_ICON = IconUtils.resize("./assets/actions/next_day_icon.png", 50, 50);
    public static final Icon REGISTER_ICON = IconUtils.resize("./assets/actions/register_icon.png", 50, 50);
    public static final Icon EXIT_ICON = IconUtils.resize("./assets/actions/exit_icon.png", 50, 50);

    public static final String NO_TILE_SELECTED = "No tile selected.";

    private JPanel actionsPanel;
    private JPanel mainPanel;
    private JPanel tilesPanel;
    private JPanel infoPanel;
    private JPanel playerStatsPanel;
    private JPanel tileInfoPanel;
    private JPanel actionReportPanel;

    private JButton plowButton;
    private JButton plantButton;
    private JButton harvestButton;
    private JButton waterButton;
    private JButton fertilizeButton;
    private JButton digButton;
    private JButton mineButton;
    private JButton nextDayButton;
    private JButton registerButton;
    private JButton exitButton;

    private ArrayList<JButton> tilePanelButtons;

    private JTextArea playerStatsTextArea;
    private JTextArea tileInfoTextArea;
    private JTextArea actionReportTextArea;

    /**
     * Creates and instantiates the view.
     */
    public MyFarmGUI() {
        this.setTitle("My Farm");
        this.setIconImage(new ImageIcon(MAIN_WINDOW_ICON_PATH).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1280, 720);
        this.setResizable(false);
        initialize();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Sets up the frame, panels, layouts, and everything else that the player
     * sees when playing the game.
     */
    private void initialize() {
        // Main panel which contains the farm lot and information panel
        mainPanel = new JPanel(new BorderLayout());

        actionsPanel = new JPanel(new GridLayout(10, 1, 0, 5));
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Buttons for actions panel
        plowButton = new JButton("Plow", PLOW_ICON);
        plantButton = new JButton("Plant", PLANT_ICON);
        harvestButton = new JButton("Harvest", HARVEST_ICON);
        waterButton = new JButton("Water", WATER_ICON);
        fertilizeButton = new JButton("Fertilize", FERTILIZE_ICON);
        digButton = new JButton("Dig", DIG_ICON);
        mineButton = new JButton("Mine", MINE_ICON);
        nextDayButton = new JButton("Next Day", NEXT_DAY_ICON);
        registerButton = new JButton("Register", REGISTER_ICON);
        exitButton = new JButton("Exit", EXIT_ICON);

        plowButton.setActionCommand("Plow");
        plantButton.setActionCommand("Plant");
        harvestButton.setActionCommand("Harvest");
        waterButton.setActionCommand("Water");
        fertilizeButton.setActionCommand("Fertilize");
        digButton.setActionCommand("Dig");
        mineButton.setActionCommand("Mine");
        nextDayButton.setActionCommand("Next Day");
        registerButton.setActionCommand("Register");
        exitButton.setActionCommand("Exit");

        plowButton.setToolTipText("Plows a tile for " + Tile.PLOW_COST + " Objectcoins so crops can grow.");
        plantButton.setToolTipText("Plants a crop on a tile to grow and sell for profit.");
        harvestButton.setToolTipText("Harvests a crop for " + Tile.HARVEST_COST + " Objectcoins and sells it.");
        waterButton.setToolTipText("Waters a crop for " + Tile.WATER_COST + " Objectcoins.");
        fertilizeButton.setToolTipText("Gives fertilizer to a crop for " + Tile.FERTILIZE_COST + " Objectcoins.");
        digButton.setToolTipText("Removes a withered crop from a tile for " + Tile.DIG_COST + " Objectcoins.");
        mineButton.setToolTipText("Removes a rock from a tile for " + Tile.MINE_COST + " Objectcoins.");
        nextDayButton.setToolTipText("Let crops grow and move on to the next day.");
        registerButton.setToolTipText("Register for a rank for additional perks and benefits.");
        exitButton.setToolTipText("Quits the game.");

        actionsPanel.add(plowButton);
        actionsPanel.add(plantButton);
        actionsPanel.add(harvestButton);
        actionsPanel.add(waterButton);
        actionsPanel.add(fertilizeButton);
        actionsPanel.add(digButton);
        actionsPanel.add(mineButton);
        actionsPanel.add(nextDayButton);
        actionsPanel.add(registerButton);
        actionsPanel.add(exitButton);

        this.add(actionsPanel, BorderLayout.EAST);

        // Tiles panel
        tilesPanel = new JPanel(new GridLayout(5, 10, 40, 8));
        tilesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tilesPanel.setBackground(Color.decode("#593D28"));

        for (int i = 0; i < 50; i++) {
            JButton tileButton = new JButton();
            tileButton.setActionCommand("Tile");
            tilesPanel.add(tileButton);
        }

        // Information panel
        infoPanel = new JPanel(new GridLayout(1, 3));
        infoPanel.setPreferredSize(new Dimension(1280, 260));

        // Player stats panel
        playerStatsPanel = new JPanel(new BorderLayout());
        playerStatsPanel.add(new JLabel("Player Statistics", SwingConstants.CENTER), BorderLayout.NORTH);
        playerStatsTextArea = new JTextArea();
        playerStatsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        playerStatsTextArea.setOpaque(false);
        playerStatsTextArea.setEditable(false);
        playerStatsPanel.add(playerStatsTextArea, BorderLayout.CENTER);
        infoPanel.add(playerStatsPanel);

        // Tile info panel
        tileInfoPanel = new JPanel(new BorderLayout());
        tileInfoPanel.add(new JLabel("Tile Information", SwingConstants.CENTER), BorderLayout.NORTH);
        tileInfoPanel.setBackground(Color.decode("#D3D3D3"));
        tileInfoTextArea = new JTextArea();
        tileInfoTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tileInfoTextArea.setOpaque(false);
        tileInfoTextArea.setEditable(false);
        tileInfoPanel.add(tileInfoTextArea, BorderLayout.CENTER);
        infoPanel.add(tileInfoPanel);

        // Action report panel
        actionReportPanel = new JPanel(new BorderLayout());
        actionReportPanel.add(new JLabel("Action Report", SwingConstants.CENTER), BorderLayout.NORTH);
        actionReportTextArea = new JTextArea();
        actionReportTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        actionReportTextArea.setOpaque(false);
        actionReportTextArea.setEditable(false);
        actionReportPanel.add(actionReportTextArea);
        infoPanel.add(actionReportPanel);

        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        mainPanel.add(tilesPanel, BorderLayout.CENTER);
        this.add(mainPanel);

        compileTilePanelButtons();
    }

    /**
     * Opens the Buy Crop menu. It allows the player to select the crop they
     * wish to plant.
     * @param frame   The parent frame the Buy Crop menu will be modal to.
     * @return   The crop the player wants to plant. Can be null.
     */
    public Crop openBuyCropMenu(JFrame frame) {
        return new BuyCropMenu(frame).openMenu();
    }

    /**
     * Opens the Rank Registration menu. It allows the player to register for
     * a new farmer type/rank.
     * @param frame   The parent frame the Rank Registration menu will be
     *                modal to.
     * @return   The new farmer type/rank the player wants to register for.
     *           Can be null.
     */
    public Stats openRankRegistrationMenu(JFrame frame) {
        return new RankRegistrationMenu(frame).openMenu();
    }

    /**
     * Updates a tile button given a tile.
     * @param tile     The tile which state is going to be used.
     * @param button   The button to be updated.
     */
    public void updateTile(Tile tile, JButton button) {
        if (tile == null || button == null) {
            return;
        }

        if (tile.hasRock()) {
            button.setIcon(TILE_WITH_ROCK);
            return;
        }

        if (!tile.isPlowed()) {
            button.setIcon(UNPLOWED_TILE);
            return;
        }

        if (!tile.hasCrop()) {
            button.setIcon(PLOWED_TILE_EMPTY);
            return;
        }

        if (tile.getPlantedCrop().isWithered()) {
            updateTileWithWitheredCrop(tile, button);
            return;
        }

        if (tile.getPlantedCrop().isHarvestable()) {
            updateTileWithGrownCrop(tile, button);
            return;
        }

        updateTileWithYoungCrop(tile, button);
    }

    /**
     * Updates the given tile butotn with an image of a withered crop using
     * the given tile.
     * @param tile     The tile that has the crop.
     * @param button   The button to be updated.
     */
    private void updateTileWithWitheredCrop(Tile tile, JButton button) {
        switch (tile.getPlantedCrop().getName()) {
            case "Turnip" -> button.setIcon(TURNIP_WITHERED);
            case "Carrot" -> button.setIcon(CARROT_WITHERED);
            case "Potato" -> button.setIcon(POTATO_WITHERED);
            case "Rose" -> button.setIcon(ROSE_WITHERED);
            case "Tulips" -> button.setIcon(TULIPS_WITHERED);
            case "Sunflower" -> button.setIcon(SUNFLOWER_WITHERED);
            case "Mango" -> button.setIcon(MANGO_WITHERED);
            case "Apple" -> button.setIcon(APPLE_WITHERED);
        }
    }

    /**
     * Updates the given tile button with an image of a young crop using the
     * given tile.
     * @param tile     The tile that has the crop.
     * @param button   The button to be updated.
     */
    private void updateTileWithYoungCrop(Tile tile, JButton button) {
        switch (tile.getPlantedCrop().getName()) {
            case "Turnip" -> button.setIcon(TURNIP_YOUNG);
            case "Carrot" -> button.setIcon(CARROT_YOUNG);
            case "Potato" -> button.setIcon(POTATO_YOUNG);
            case "Rose" -> button.setIcon(ROSE_YOUNG);
            case "Tulips" -> button.setIcon(TULIPS_YOUNG);
            case "Sunflower" -> button.setIcon(SUNFLOWER_YOUNG);
            case "Mango" -> button.setIcon(MANGO_YOUNG);
            case "Apple" -> button.setIcon(APPLE_YOUNG);
        }
    }

    /**
     * Updates the given tile button with an image of a grown crop using the
     * given tile.
     * @param tile     The tile that has the crop.
     * @param button   The button to be updated.
     */
    private void updateTileWithGrownCrop(Tile tile, JButton button) {
        switch (tile.getPlantedCrop().getName()) {
            case "Turnip" -> button.setIcon(TURNIP_GROWN);
            case "Carrot" -> button.setIcon(CARROT_GROWN);
            case "Potato" -> button.setIcon(POTATO_GROWN);
            case "Rose" -> button.setIcon(ROSE_GROWN);
            case "Tulips" -> button.setIcon(TULIPS_GROWN);
            case "Sunflower" -> button.setIcon(SUNFLOWER_GROWN);
            case "Mango" -> button.setIcon(MANGO_GROWN);
            case "Apple" -> button.setIcon(APPLE_GROWN);
        }
    }

    /**
     * Updates all the button tiles with the proper images given the
     * corresponding tile.
     * @param buttonToTile   A map that maps each tile button to each tile.
     */
    public void updateAllTiles(Map<JButton, Tile> buttonToTile) {
        for (JButton tileButton : tilePanelButtons) {
            updateTile(buttonToTile.get(tileButton), tileButton);
        }
    }

    /**
     * Updates the player's displayed statistics using the given stats object.
     * @param stats   The group of statistics to be displayed.
     */
    public void updatePlayerStats(Stats stats) {
        playerStatsTextArea.setText(stats.getBasicAndFarmingStats());
    }

    /**
     * Updates the displayed selected tile's information using a given tile.
     * @param tile   The tile which info is to be displayed.
     */
    public void updateSelectedTileInfo(Tile tile) {
        String text = NO_TILE_SELECTED;
        if (tile != null) {
            text = tile.getDetails();
        }
        tileInfoTextArea.setText(text);
    }

    /**
     * Updates the action report information that is displayed using the given
     * action report.
     * @param report   The report which info is to be displayed.
     */
    public void updateActionReport(ActionReport report) {
        if (report == null) {
            return;
        }
        actionReportTextArea.setText(report.getDetails());
    }

    /**
     * Terminates the program.
     */
    public void exitProgram() {
        System.exit(0);
    }

    /**
     * Adds the given action listener to the actions panel buttons.
     * @param listener   The action listener to be added.
     */
    public void setActionsPanelListener(ActionListener listener) {
        Component[] components = actionsPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JButton) {
                JButton button = (JButton)components[i];
                button.addActionListener(listener);
            }
        }
    }

    /**
     * Adds the given action listener to the tiles panel buttons.
     * @param listener   The action listener to be added.
     */
    public void setTilesPanelListener(ActionListener listener) {
        Component[] components = tilesPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JButton) {
                JButton button = (JButton)components[i];
                button.addActionListener(listener);
            }
        }
    }

    /**
     * Compiles all the tile panel buttons into an ArrayList.
     * @return   The ArrayList of tile panel buttons.
     */
    public ArrayList<JButton> compileTilePanelButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        Component[] components = tilesPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                buttons.add((JButton)comp);
            }
        }
        tilePanelButtons = buttons;
        return tilePanelButtons;
    }

    /**
     * Returns an ArrayList of the tile panel buttons.
     * @return   An ArrayList of the tile panel buttons.
     */
    public ArrayList<JButton> getTilePanelButtons() {
        return tilePanelButtons;
    }
}
