package image.processing.engine;
/**
 * Image
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Image {
    private static BufferedImage originalImage = null;
    private static BufferedImage resultImage = null;
    public static final String FILE_FORMAT = "bmp";
    public static int width;
    public static int height;
    private static Image instance;


    private Image(){
    }

    public static BufferedImage getOriginalImage() {
        return originalImage;
    }

    public static void setOriginalImage(BufferedImage originalImage) {
        Image.originalImage = originalImage;
    }

    public static BufferedImage getResultImage() {
        return resultImage;
    }

    public static void setResultImage(BufferedImage resultImage) {
        Image.resultImage = resultImage;
    }

    public static synchronized Image getInstance() {
        if(instance==null)
            instance = new Image();
        return instance;
    }

    public void setInstance(Image instance) {
        this.instance = instance;
    }

    /**
     * Function that opens the image that have the specified
     * path
     * @param path to locate the image to open
     * @return true if image opening was sucefful else false
     */
    public static boolean openImage(String path) {
        try {
            originalImage = ImageIO.read(new File(path));
            width = originalImage.getWidth();
            height = originalImage.getHeight();
            resultImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            //resultImage = ImageIO.read(new File(path));
        } catch (Exception e) {
            System.out.println("!Error opening file!");
            System.out.println(" The stack trace is: ");
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    /**
     *  Method that save the resultImage to the path specified.
     *
     * @param path where the result of the rotation operation will be saved
     * @param resultImage
     */
    public static void saveTo(String path,BufferedImage resultImage) {
        try {
            File result = new File(path);
            if(result.exists())
                result.createNewFile();        
            ImageIO.write(resultImage,FILE_FORMAT, result);
        } catch (Exception e) { //Cath possible Exception that can arise from saving or opening a file
            System.out.println("!Error opening file!");
            System.out.println(" The stack trace is: ");
            System.out.println(e.getStackTrace());
        }
    }


}