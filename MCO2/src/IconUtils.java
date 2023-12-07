import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Utils class for dealing with Icons and ImageIcons.
 */
public final class IconUtils {
    private IconUtils() {}

    /**
     * Creates and resizes an ImageIcon given a path to an image, and the
     * desired dimensions.
     * @param imagePath   The file path to the image.
     * @param width       The width of the image.
     * @param height      The height of the image.
     * @return   An ImageIcon with the desired image and dimensions.
     */
    public static Icon resize(String imagePath, int width, int height) {
        return resize(new ImageIcon(imagePath), width, height);
    }

    /**
     * Creates and resizes an ImageIcon given an ImageIcon and desired
     * dimensions.
     * @param icon     The ImageIcon to be displayed.
     * @param width    The width of the image.
     * @param height   The height of the image.
     * @return   An ImageIcon with the desired ImageIcon and dimensions.
     */
    public static Icon resize(ImageIcon icon, int width, int height) {
        Image resizedImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
