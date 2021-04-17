package image.processing.engine.controllers;

import image.processing.engine.constants.*;
import image.processing.engine.generator.InfoScreen;
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
    private static Scene scene;
    private static Logger logger;
    private static Style currentStyle = Style.DARK;

    @Override
    public void start(Stage stage) {
        initialize(this, stage);
        display(AppScreen.HOME);
    }

    /**
     * Method to initialize the needed internal instance variables, after running
     * the app has all the internal references initialized
     *
     * @param stage - the primary stage of the app
     */
    private static void initialize(MainController currentApp, Stage stage) {
        MainController.stage = stage;
        MainController.instance = currentApp;
        MainController.logger = Logger.getLogger(MainController.class.getName());
    }

    /**
     * This method changes the current screen of the app based on the <b>screen</b>
     * parameter. This method enables the navigation within the App.
     * 
     * @param screen - the screen name that will be displayed
     */
    public static boolean display(AppScreen screen) {
        if (screen==AppScreen.INFO)
            return display(InfoScreen.build());
            
        try {
            Parent root = FXMLLoader.load(instance.getClass().getResource(screen.getPath()));
            scene = new Scene(root);
            scene.getStylesheets().add(currentStyle.getPath());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(APP_NAME);
            stage.show();
            return true;
        } catch (Exception exception) {
            logger.log(SEVERE, "The app couldn't start correctly", exception);
            return false;
        }
    }

    public static boolean display(Parent layout) {
        try {
            scene = new Scene(layout);
            scene.getStylesheets().add(currentStyle.getPath());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(APP_NAME);
            stage.show();
            return true;
        } catch (Exception exception) {
            logger.log(SEVERE, "The app couldn't start correctly", exception);
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void switchTheme() {
        if(currentStyle == Style.LIGHT)
            currentStyle = Style.DARK;
        else {
            currentStyle = Style.LIGHT;
        }
        scene.getStylesheets().remove(0);
        scene.getStylesheets().add(currentStyle.getPath());
    }

}
