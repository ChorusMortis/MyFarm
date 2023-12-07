import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * The class that contains {@code main()}, the main entrypoint of the program.
 */
public class Driver {
    
    /**
     * Starts the program.
     * @param args   Not used. Does not parse or use arguments.
     * @throws IOException When the player provides invalid input data.
     */
    public static void main(String[] args) throws IOException {
        startGame();
    }

    /**
     * Starts the game.
     * @throws IOException When the player provides invalid input data.
     */
    private static void startGame() throws IOException {
        int numberOfRocks = getNumberOfRocks();
        MyFarm model = new MyFarm(numberOfRocks);
        MyFarmGUI gui = new MyFarmGUI();
        MyFarmController controller = new MyFarmController(model, gui);
    }

    /**
     * Gets the number of rocks to initialize the game with from the player
     * via file chooser.
     * @return The number of rocks to initialize the game with. It is no less
     * than 10 and no more than 30.
     * @throws IOException When the player provides invalid input data.
     */
    private static int getNumberOfRocks() throws IOException {
        String msg = "Select a plaintext file that contains the number of rocks to initialize the game with.\n"
                   + "It must be no less than 10 and no more than 30.";

        JOptionPane.showMessageDialog(null, msg, "Select input file", JOptionPane.INFORMATION_MESSAGE);
        
        JFileChooser fileChooser = new JFileChooser(new File("."));
        int response = fileChooser.showOpenDialog(fileChooser);
        if (response != JFileChooser.APPROVE_OPTION) {
            throw new IOException("Missing input file for initial rock count");
        }

        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int numberOfRocks = Integer.parseInt(reader.readLine());
        reader.close();

        if (numberOfRocks < 10 || numberOfRocks > 30) {
            throw new IllegalArgumentException("Number of rocks has to be less than 10 and greater than 30");
        }

        return numberOfRocks;
    }
}
