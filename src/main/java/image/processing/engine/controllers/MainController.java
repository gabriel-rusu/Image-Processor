package image.processing.engine.controllers;

import image.processing.engine.constants.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.util.logging.*;
import javafx.application.*;
import static java.util.logging.Level.*;

public class MainController extends Application {
    private static final String APP_NAME = "Image Processor";
    private static MainController instance;
    private static Stage stage;
    private static Logger logger;

    @Override
    public void start(Stage stage) {
        initialize(this,stage);
        display(AppScreen.HOME);
    }

    /**
     * Method to initialize the needed internal instance variables,
     * after running the app has all the internal references initialized
     *
     * @param stage - the primary stage of the app
     */
    private static void initialize( MainController currentApp, Stage stage) {
        MainController.stage = stage;
        MainController.instance = currentApp;
        MainController.logger = Logger.getLogger(MainController.class.getName());
    }

    
    /**
     * This method changes the current screen of the app based 
     * on the <b>screen</b> parameter. This method enables the 
     * navigation within the App.
     * 
     * @param screen - the screen name that will be displayed
     */
    public static void display(AppScreen screen) {
        try {
            Parent root = FXMLLoader.load(instance.getClass().getResource(screen.getPath()));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(APP_NAME);
            stage.show();
        } catch (Exception exception) {
            logger.log(SEVERE, "The app couldn't start correctly", exception);
        }
    }


    public static void display(Parent layout) {
        try{
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(APP_NAME);
            stage.show();
        }catch (Exception exception) {
            logger.log(SEVERE, "The app couldn't start correctly", exception);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
