package image.processing.engine.controllers;

import java.io.*;
import java.net.*;

import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import image.processing.engine.constants.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import static java.util.logging.Level.*;

public class ImageRotatorController implements Initializable {
    private static final String TEMP_BMP = "temp.bmp";
    private File imageDirectory;
    private Logger logger;
    private File[] images;

    @FXML
    /**
     * the image view that will display the selected input image
     */
    ImageView inputImage;

    @FXML
    /**
     * the image view that will display the image after the rotation operation
     */
    ImageView outputImage;

    @FXML
    /**
     * the 90 degree rotation option
     */
    RadioButton degreeOption1;

    @FXML
    /**
     * the 180 degree rotation option
     */
    RadioButton degreeOption2;

    @FXML
    /**
     * the 270 degree rotation option
     */
    RadioButton degreeOption3;

    @FXML
    /**
     * the name of the result image
     */
    TextField targetFileName;

    @FXML
    /**
     * the combo box that will contain all the available .bmp images
     */
    ComboBox<String> options;

    /**
     * the group that will hold all the radio button options
     */
    private ToggleGroup group;

    /**
     * the file of the selected image
     */
    private File selectedImage = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Create the logger used to log all the possible errors
        this.logger = Logger.getLogger(ImageRotatorController.class.getName());
        // Create a toggle group for all the degree options
        this.group = new ToggleGroup();
        // add all the degree option to the toggle group
        group.getToggles().addAll(degreeOption1, degreeOption2, degreeOption3);
        try {
            // get the uri to the images in the resources
            URI uri = getClass().getClassLoader().getResource("images").toURI();
            // get the image directory where all the images are located
            this.imageDirectory = new File(Paths.get(uri).toString());
            // filter all the entries of the image directory and keep only the files(images)
            this.images = imageDirectory.listFiles(file -> file.isFile());

            // loop through all the images and take their file system name and add them to
            // the available options
            for (File image : images) {
                options.getItems().add(image.getName());
            }
            // register an event handler for option selection through a method reference
            options.setOnAction(this::displaySelected);

        } catch (Exception exception) {
            // Handle possible errors that can arise when trying to get all the image file
            // name/ path to the images directory
            logger.log(SEVERE, "Something went wrong opening directory 'images' ", exception);
        }

    }

    /**
     * Method that handles the selection of an input image. This method takes the
     * name of the selected image and loads the corresponding bmp image in the input
     * image instance
     * 
     * @param selection - the selection action
     */
    private void displaySelected(ActionEvent selection) {
        // get the selected image name
        String selectedOption = options.getSelectionModel().getSelectedItem();

        // find the image and set it in the image view
        for (File image : this.images) {
            if (selectedOption.equals(image.getName())) {
                selectedImage = image;
            }
        }

        // Create a FileInputStream from the image file
        try (FileInputStream inputStream = new FileInputStream(selectedImage)) {
            // set the newly created image
            inputImage.setImage(new Image(inputStream));
        } catch (Exception exception) {
            // Log all the possible errors that could arise when creating a FileInputStream
            // from a file
            logger.log(SEVERE, "Something went wrong opening the selected image: " + selectedOption, exception);
        }
    }

    /**
     * Method used to go back to the main screen when the back button is clicked
     */
    public void goBack() {
        MainController.display(AppScreen.HOME);
    }

    /**
     * Method used to switch the app theme when the user toggles/untoggles the
     * switch theme button
     */
    public void switchTheme() {
        MainController.switchTheme();
    }

    /**
     * Method that apply the rotation operation on the selected image and displays
     * the result image by setting the outputImage variable
     */
    public void processImage() {
        String outputFileName;
        // Get the selected rotational option
        RadioButton selectedButton = (RadioButton) this.group.getSelectedToggle();

        // if the process action has invalid options => an alert screen will be shown
        if (selectedButton == null || selectedImage == null) {
            // Create an error alert window and set the title and message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Parameters");
            String message = selectedImage == null ? "Please select an input image!"
                    : "Please select how to rotate the image";

            // Header Text: null
            alert.setHeaderText(null);
            // Set the appropriate error message
            alert.setContentText(message);
            // display the alert screen and wait for the user to close it
            alert.showAndWait();
            return;
        }
        // Get the number of degrees selected by the user
        String selectedLabel = selectedButton.getText();
        // Remove the degree symbol
        String degrees = selectedLabel.substring(0, selectedLabel.length() - 1);

        // if the user didn't supply a target name for the image make a temp image
        if (this.targetFileName.getText().isEmpty())
            outputFileName = TEMP_BMP;
        else
            outputFileName = this.targetFileName.getText();
        // process the selected image and save the result in the outputFileName
        image.processing.engine.models.Image.open(selectedImage.getAbsolutePath()).apply(ImageOperation.ROTATE, degrees)
                .saveTo(outputFileName);

        // open the newly created image
        File result = new File(outputFileName);
        // try to make a FileInputStream and create a image for the output image view
        try (FileInputStream image = new FileInputStream(result)) {
            outputImage.setImage(new Image(image));

        } catch (Exception exception) {
            // Log all the possible errors that could arise when creating a FileInputStream
            // from a file
            logger.log(SEVERE, "Could not save the image tmp.bmp!", exception);
        } finally {
            // If the user didn't specified a file name then delete the temporary image file
            if (result.getName().equals(TEMP_BMP)) {
                this.logger.info("Deleted the temporary temp.bmp image!");
                result.delete();
            }
        }
    }
}