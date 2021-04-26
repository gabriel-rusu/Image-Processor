package image.processing.engine.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import image.processing.engine.constants.AppScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HomeController implements Initializable {

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Here I initialize all the needed components

    }

    @FXML
    /**
     * Method used to go to the app gallery screen when the gallery button is
     * clicked
     */
    public void goToGallery() {
        MainController.display(AppScreen.GALLERY);
    }

    @FXML
    /**
     * Method used to switch the app theme when the user toggles/untoggles the
     * switch theme button
     */
    public void switchTheme() {
        MainController.switchTheme();
    }

    @FXML
    /**
     * Method used to go to the info screen when the info button is clicked
     */
    public void goToInfo() {
        MainController.display(AppScreen.INFO);
    }

    @FXML
    /**
     * Method used to go to the image processing screen when the image processor
     * button is clicked
     */
    public void goToImageRotation() {
        MainController.display(AppScreen.IMAGE_ROTATOR);
    }
}
