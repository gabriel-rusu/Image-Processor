package image.processing.engine.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import image.processing.engine.constants.*;

/**
 * Unit test for simple App.
 */
public class ImageTest 
{
    private static final String LAND_PICTURE = "/images/LAND2.bmp";
    private Image image;

    @BeforeEach
    public void setUp() {
        this.image = new Image(Image.class.getResource(LAND_PICTURE).getPath());
    }

    @Test
    public void shouldCreateEmptyImage()
    {
        Image image = new Image();
        assertNotNull(image);
        assertNull(image.getBufferedImage());
    }

    @Test
    public void shouldCreateImageFromPicture() {
        Image image = new Image(Image.class.getResource(LAND_PICTURE).getPath());
        assertNotNull(image);
        assertNotNull(image.getBufferedImage());
    }

    @Test
    public void shouldRotateImage() {
        Image resultImage = this.image.apply(ImageOperation.ROTATE, "90");
        assertNotNull(resultImage);
        assertNotNull(resultImage.getBufferedImage());
        try {
            assertTrue(resultImage.saveTo("/LAND.bmp"));
        } catch(Exception exception) {
            assertTrue(true);
        }
    }
}
