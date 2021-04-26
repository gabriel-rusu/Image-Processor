package image.processing.engine.controllers;

import java.io.*;
import java.net.*;
import javafx.fxml.*;
import java.nio.file.Paths;

import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import static java.util.logging.Level.*;
import image.processing.engine.constants.*;

public class GalleryController implements Initializable {
    private File imageDirectory;
    private Logger logger;
    private File[] images;

    @FXML
    /** the list of all images that the user can use */
    private ListView<String> imagesList;

    @FXML
    /** the selected image that will be displayed in the right accordion panel */
    private ImageView imageView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Create the logger used to log all the possible errors/ informations
        this.logger = Logger.getLogger(GalleryController.class.getName());
        this.logger.info("Initializing the GalleryController.class");
        try {
            // get the uri to the images in the resources
            URI uri = getClass().getClassLoader().getResource("images").toURI();
            // get the image directory where all the images are located
            this.imageDirectory = new File(Paths.get(uri).toString());
            // filter all the entries of the image directory and keep only the files(images)
            this.images = imageDirectory.listFiles(file -> file.isFile());

            // loop through all the images and take their file system name
            for (File image : images) {
                imagesList.getItems().add(image.getName());
            }
            // disable the image list editable option
            imagesList.setEditable(false);
            // register a event handler through a method reference
            imagesList.setOnMouseReleased(this::displaySelectedImage);
            // set the image selected as being the first from the list view
            imageView.setImage(imageOfFile(images[0]));

        } catch (Exception exception) {
            // Handle possible errors that can arise when trying to get all the image file
            // name/ path to the images directory
            this.logger.log(SEVERE, "Something went wrong opening the directory 'images' ", exception);
        }

    }

    /**
     * The method that will handle the selection of a image from the image list.
     * This method will find the selected image and will display it in the left
     * imageView
     * 
     * @param event - the selection event
     */
    private void displaySelectedImage(MouseEvent event) {
        // get the selected image name
        String selectedImage = imagesList.getSelectionModel().getSelectedItem();
        if (selectedImage == null)
            return;
        // find the image and set it in the image view
        for (File image : images) {
            if (image.getName().equals(selectedImage)) {
                imageView.setImage(imageOfFile(image));
            }
        }
    }

    /**
     * Helper method that returns a javafx.scene.image.Image from a file that is
     * given as parameter
     * 
     * @param image - the file that represents the selected image
     * @return - the corresponding image
     */
    private Image imageOfFile(File image) {
        // Create a FileInputStream from the image file
        try (FileInputStream inputStream = new FileInputStream(image)) {
            // return the newly created image
            return new Image(new FileInputStream(image));
        } catch (Exception exception) {
            // Log all the possible errors that could arise when creating a FileInputStream
            // from a file
            logger.log(WARNING, "Couldn't open the image " + image.getName(), exception);
        }
        return null;
    }

    @FXML
    /**
     * Method used to go back to the main screen when the back button is clicked
     */
    public void goBack() {
        MainController.display(AppScreen.HOME);
    }

    @FXML
    /**
     * Method used to switch the app theme when the user toggles/untoggles the
     * switch theme button
     */
    public void switchTheme() {
        MainController.switchTheme();
    }
}