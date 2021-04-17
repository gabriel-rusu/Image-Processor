package image.processing.engine.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import image.processing.engine.constants.AppScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HomeController implements Initializable{

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

    @FXML
    public void goToGallery() {
        MainController.display(AppScreen.GALLERY);
    }

    @FXML
    public void switchTheme() {
        MainController.switchTheme();
    }
    
    @FXML
    public void goToInfo(){
        MainController.display(AppScreen.INFO);
    }
    

    @FXML
    public void goToImageRotation() {
        MainController.display(AppScreen.IMAGE_ROTATOR);
    }
}
