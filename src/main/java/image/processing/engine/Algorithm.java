package image.processing.engine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Algorithm
 */
public class Algorithm {

    /**
     * Function that performs the rotation operation on originalImage with degrees the result
     * will be written in resulImage
     *
     * @param degrees - one of the  following values{90, 180, 270}
     * @param originalImage - a BufferedImage that contains the original image
     * @param resultImage - a BufferedIamge that will contain the rotated image
     */
    public static void rotateImageBy(int degrees, BufferedImage originalImage, BufferedImage resultImage) {
        int numberOfRotations = (degrees / 90);
        for (int count = 0; count < numberOfRotations; count++) {
            for (int i = 0; i < Image.width / 2; i++)
                for (int j = i; j < Image.height - i - 1; j++) {
                    Color temp = new Color(originalImage.getRGB(i, j));
                    resultImage.setRGB(i, j, originalImage.getRGB(j, Image.width - 1 - i));
                    resultImage.setRGB(j, Image.height - i - 1,
                            originalImage.getRGB(Image.width - 1 - i, Image.height - j - 1));
                    resultImage.setRGB(Image.width - 1 - i, Image.height - j - 1,
                            originalImage.getRGB(Image.width - 1 - j, i));
                    resultImage.setRGB(Image.width - 1 - j, i, temp.getRGB());
                }
            originalImage = resultImage;
        }
    }
}