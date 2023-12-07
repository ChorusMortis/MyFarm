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
 * Contains the data for the Rank Registration menu.
 */
public class RankRegistrationMenu extends JDialog {
    public static final String MENU_ICON_PATH = "./assets/application/rank_registration_menu_icon.png";

    public static final Icon REGISTERED_ICON = IconUtils.resize("./assets/ranks/registered.png", 75, 75);
    public static final Icon DISTINGUISHED_ICON = IconUtils.resize("./assets/ranks/distinguished.png", 75, 75);
    public static final Icon LEGENDARY_ICON = IconUtils.resize("./assets/ranks/legendary.png", 75, 75);
    public static final Icon CANCEL_ICON = IconUtils.resize("./assets/ranks/cancel.png", 75, 75);

    private Stats farmerRank;
    private JTextArea rankDesc;

    /**
     * Creates an instance of the menu.
     * @param frame   The parent frame this menu will be modal to.
     */
    public RankRegistrationMenu(JFrame frame) {
        super(frame, "Rank Registration", true);
        this.setIconImage(new ImageIcon(MENU_ICON_PATH).getImage());
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize((new Dimension(1024, 576)));
        this.setResizable(false);
    }

    /**
     * Opens the Rank Registration menu and allows the player to register for
     * a new farmer type/rank.
     * @return   Stats instance containing the new stats of the farmer
     *           type/rank the player wants to register for. Can be null.
     */
    public Stats openMenu() {
        JLabel header = new JLabel("Register for a Rank", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1024, 50));

        JPanel ranksPanel = new JPanel(new GridLayout(1, 3));
        ranksPanel.setPreferredSize(new Dimension(1024, 150));
        ranksPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel rankDescPanel = new JPanel(new BorderLayout());
        rankDescPanel.add(ranksPanel, BorderLayout.NORTH);

        JButton registeredButton = new JButton("Registered");
        JButton distinguishedButton= new JButton("Distinguished");
        JButton legendaryButton = new JButton("Legendary");
        JButton nothingButton = new JButton("Cancel");

        registeredButton.setIcon(REGISTERED_ICON);
        distinguishedButton.setIcon(DISTINGUISHED_ICON);
        legendaryButton.setIcon(LEGENDARY_ICON);
        nothingButton.setIcon(CANCEL_ICON);

        rankDesc = new JTextArea();
        rankDesc.setOpaque(false);
        rankDesc.setEditable(false);

        rankDescPanel.add(rankDesc, BorderLayout.CENTER);
        rankDescPanel.add(Box.createRigidArea(new Dimension(425, 0)), BorderLayout.WEST);
        rankDescPanel.add(Box.createRigidArea(new Dimension(300, 0)), BorderLayout.EAST);

        JPanel bottomSubPanel = new JPanel(new FlowLayout());
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> this.setVisible(false));
        bottomSubPanel.add(exitButton);
        rankDescPanel.add(bottomSubPanel, BorderLayout.SOUTH);

        registeredButton.addActionListener(e -> {
            farmerRank = Stats.createRegisteredRank();
            rankDesc.setText(farmerRank.getFarmingAndRankStats());
        });
        distinguishedButton.addActionListener(e -> {
            farmerRank = Stats.createDistinguishedRank();
            rankDesc.setText(farmerRank.getFarmingAndRankStats());
        });
        legendaryButton.addActionListener(e -> {
            farmerRank = Stats.createLegendaryRank();
            rankDesc.setText(farmerRank.getFarmingAndRankStats());
        });
        nothingButton.addActionListener(e -> {
            farmerRank = null;
            rankDesc.setText("Cancel registration and exit the menu.");
        });

        ranksPanel.add(registeredButton);
        ranksPanel.add(distinguishedButton);
        ranksPanel.add(legendaryButton);
        ranksPanel.add(nothingButton);

        this.add(header, BorderLayout.NORTH);
        this.add(rankDescPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        return farmerRank;
    }
}
