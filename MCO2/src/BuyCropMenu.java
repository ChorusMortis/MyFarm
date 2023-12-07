import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Contains the data for the Buy Crop menu.
 */
public class BuyCropMenu extends JDialog {
    public static final String MENU_ICON_PATH = "./assets/application/buy_crop_menu_icon.png";

    public static final Icon TURNIP_SEEDS = IconUtils.resize("./assets/crops/seeds/turnip_seeds.png", 75, 75);
    public static final Icon CARROT_SEEDS = IconUtils.resize("./assets/crops/seeds/carrot_seeds.png", 75, 75);
    public static final Icon POTATO_SEEDS = IconUtils.resize("./assets/crops/seeds/potato_seeds.png", 75, 75);
    public static final Icon ROSE_SEEDS = IconUtils.resize("./assets/crops/seeds/rose_seeds.png", 75, 75);
    public static final Icon TULIPS_SEEDS = IconUtils.resize("./assets/crops/seeds/tulips_seeds.png", 75, 75);
    public static final Icon SUNFLOWER_SEEDS = IconUtils.resize("./assets/crops/seeds/sunflower_seeds.png", 75, 75);
    public static final Icon MANGO_SEEDS = IconUtils.resize("./assets/crops/seeds/mango_seeds.png", 75, 75);
    public static final Icon APPLE_SEEDS = IconUtils.resize("./assets/crops/seeds/apple_seeds.png", 75, 75);
    public static final Icon NO_SEEDS = IconUtils.resize("./assets/crops/seeds/no_seeds.png", 75, 75);

    private Crop selectedCrop;
    private JTextArea selectedCropDescription;

    /**
     * Creates an instance of the menu.
     * @param frame   The parent frame this menu will be modal to.
     */
    public BuyCropMenu(JFrame frame) {
        super(frame, "Buy Crop", true);
        this.setIconImage(new ImageIcon(MENU_ICON_PATH).getImage());
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize((new Dimension(1024, 576)));
        this.setResizable(false);
    }

    /**
     * Opens the Buy Crop menu and allows the player to select a crop to buy.
     * @return   The crop the user wants to buy. Can be null.
     */
    public Crop openMenu() {
        JLabel header = new JLabel("Buy a Crop", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1024, 50));

        JPanel menuPanel = new JPanel(new GridLayout(1, 9, 5, 5));
        menuPanel.setPreferredSize(new Dimension(1024, 150));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(menuPanel, BorderLayout.NORTH);

        JButton buyTurnipButton = new JButton(TURNIP_SEEDS);
        JButton buyCarrotButton = new JButton(CARROT_SEEDS);
        JButton buyPotatoButton = new JButton(POTATO_SEEDS);
        JButton buyRoseButton = new JButton(ROSE_SEEDS);
        JButton buyTulipsButton = new JButton(TULIPS_SEEDS);
        JButton buySunflowerButton = new JButton(SUNFLOWER_SEEDS);
        JButton buyMangoButton = new JButton(MANGO_SEEDS);
        JButton buyAppleButton = new JButton(APPLE_SEEDS);
        JButton nothingButton = new JButton(NO_SEEDS);

        selectedCropDescription = new JTextArea();
        selectedCropDescription.setOpaque(false);
        selectedCropDescription.setEditable(false);
        
        subPanel.add(selectedCropDescription, BorderLayout.CENTER);
        subPanel.add(Box.createRigidArea(new Dimension(425, 0)), BorderLayout.WEST);
        subPanel.add(Box.createRigidArea(new Dimension(300, 0)), BorderLayout.EAST);
        
        JPanel bottomSubPanel = new JPanel(new FlowLayout());
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> this.setVisible(false));
        bottomSubPanel.add(exitButton);
        subPanel.add(bottomSubPanel, BorderLayout.SOUTH);

        buyTurnipButton.addActionListener(e -> {
            selectedCrop = new Turnip();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buyCarrotButton.addActionListener(e ->  {
            selectedCrop = new Carrot();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buyPotatoButton.addActionListener(e ->  {
            selectedCrop = new Potato();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buyRoseButton.addActionListener(e ->  {
            selectedCrop = new Rose();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buyTulipsButton.addActionListener(e ->  {
            selectedCrop = new Tulips();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buySunflowerButton.addActionListener(e ->  {
            selectedCrop = new Sunflower();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buyMangoButton.addActionListener(e ->  {
            selectedCrop = new Mango();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        buyAppleButton.addActionListener(e ->  {
            selectedCrop = new Apple();
            selectedCropDescription.setText(selectedCrop.getBaseStats());
        });
        nothingButton.addActionListener(e -> {
            selectedCrop = null;
            selectedCropDescription.setText("Exit without buying anything.");
        });

        menuPanel.add(buyTurnipButton);
        menuPanel.add(buyCarrotButton);
        menuPanel.add(buyPotatoButton);
        menuPanel.add(buyRoseButton);
        menuPanel.add(buyTulipsButton);
        menuPanel.add(buySunflowerButton);
        menuPanel.add(buyMangoButton);
        menuPanel.add(buyAppleButton);
        menuPanel.add(nothingButton);

        this.add(header, BorderLayout.NORTH);
        this.add(subPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        return selectedCrop;
    }
}
