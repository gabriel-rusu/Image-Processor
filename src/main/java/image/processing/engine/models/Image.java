package image.processing.engine.models;

import java.io.*;
import java.nio.BufferOverflowException;
import java.awt.*;
import java.util.logging.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static java.util.logging.Level.*;
import image.processing.engine.constants.ImageOperation;

public class Image {
    /**
     * The error message that will be displayed before the exception stack trace
     */
    private static final String ERROR_STACK_TRACE_MESSAGE = "The error stack trace is: ";
    /**
     * The error message that will appear if something went wrong at opening the
     * file
     */
    private static final String ERROR_MESSAGE = "Error at opening the file: ";
    private static final Logger logger = Logger.getLogger(Image.class.getName());
    private BufferedImage image = null;
    private static final String FILE_FORMAT = "bmp";
    private int width;
    private int height;

    /**
     * Creates an empty Image object
     */
    public Image() {
        this.image = null;
        this.width = 0;
        this.height = 0;
    }

    /**
     * Creates a Image object from the buffered image
     * 
     * @param image - the buffered image that will be wrapped in the Image object
     */
    public Image(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Creates a Image object from the image found at the specified path
     * 
     * @param path - path to the image
     */
    public Image(String path) {
        try {
            this.image = ImageIO.read(new File(path));
            width = this.image.getWidth();
            height = this.image.getHeight();
        } catch (Exception exception) {
            displayInfo(exception, path);
            throw new RuntimeException(exception);
        }
    }

    /**
     * Helper function that logs the possible error that can arise from image
     * manipulation
     * 
     * @param e    - thrown exception
     * @param path - the path of the image that generated the exception
     */
    private static void displayInfo(Exception e, String path) {
        logger.log(SEVERE, ERROR_MESSAGE + path, e);
        logger.log(SEVERE, ERROR_STACK_TRACE_MESSAGE + e.getMessage(), e);
    }

    public BufferedImage getBufferedImage() {
        return this.image;
    }

    public void setBufferedImage(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Function that opens the image that have the specified path
     * 
     * @param path - to locate the image to open
     * @return - the newly created image object the represents the opened image
     */
    public static Image open(String path) {
        Image image = new Image();
        try {
            image.image = ImageIO.read(new File(path));
            image.width = image.image.getWidth();
            image.height = image.image.getHeight();
        } catch (Exception exception) {
            // Cath possible Exception that can arise from opening a file or image
            Image.displayInfo(exception, path);
            throw new RuntimeException(exception);
        }
        return image;
    }

    /**
     * Function that saves the image of the current object to the path specified as
     * the parameter path
     * 
     * @param path - the path where the image will be saved
     * @return - true if the saving of the image was successful else returns false
     */
    public boolean saveTo(String path) {
        try {
            File imageFile = new File(path);
            if (imageFile.exists())
                imageFile.createNewFile();
            ImageIO.write(this.image, FILE_FORMAT, imageFile);
            logger.info(
                    "Created the image " + imageFile.getName() + " with absolute path: " + imageFile.getAbsolutePath());
        } catch (Exception e) {
            // Cath possible Exception that can arise from saving or opening a file
            Image.displayInfo(e, path);
            return false;
        }
        return true;
    }

    /**
     * Function that performs the rotation operation on originalImage with the
     * specified degrees the result will be returned
     *
     * @param degrees - one of the following values{90, 180, 270}
     */
    private BufferedImage rotateImageBy(int degrees) {
        BufferedImage result;
        BufferedImage tempImage = this.image;

        int numberOfRotations = (degrees / 90);
        for (int count = 0; count < numberOfRotations; count++) {
            result = rotateImageBy90Degrees(tempImage);
            tempImage = result;
        }
        return tempImage;
    }

    /**
     * Helper function that performs the 90 degree rotation of the image given as a
     * parameter
     * 
     * @param image - the buffered image of the manipulated image
     * @return - the rotated image as a buffered image
     */
    private BufferedImage rotateImageBy90Degrees(BufferedImage image) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage tempImage = image;
        for (int i = 0; i < width / 2; i++)
            for (int j = i; j < height - i - 1; j++) {
                Color temp = new Color(tempImage.getRGB(i, j));
                result.setRGB(i, j, tempImage.getRGB(j, width - 1 - i));
                result.setRGB(j, height - i - 1, image.getRGB(width - 1 - i, height - j - 1));
                result.setRGB(width - 1 - i, height - j - 1, tempImage.getRGB(width - 1 - j, i));
                result.setRGB(width - 1 - j, i, temp.getRGB());
            }
        return result;
    }

    /**
     * Function that performs the specified operation on the current object image
     * 
     * @param imageOperation - the type of operation that will be performed on the
     *                       image
     * @param parameters     - the parameters of the operation to be performed
     * @return - the new image resulted after the image operation
     */
    public Image apply(ImageOperation imageOperation, String... parameters) {
        // currently the only supported type of operation is the rotation operation
        if (imageOperation == ImageOperation.ROTATE) {
            int degrees = Integer.parseInt(parameters[0]);
            return new Image(rotateImageBy(degrees));
        } else
            return null;
    }

}
