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
        // save the stage & current class reference for app navigation
        MainController.stage = stage;
        MainController.instance = currentApp;
        // Create the logger that will log all the possible errors
        MainController.logger = Logger.getLogger(MainController.class.getName());
    }

    /**
     * This method changes the current screen of the app based on the <b>screen</b>
     * parameter. This method enables the navigation within the App.
     * 
     * @param screen - the screen name that will be displayed
     */
    public static boolean display(AppScreen screen) {
        // if the wanted screen is the info screen then build it and display it using the other display method
        if (screen == AppScreen.INFO)
            return display(InfoScreen.build());

        try {
            // parse and return the parent of the newly created fxml screen
            Parent root = FXMLLoader.load(instance.getClass().getResource(screen.getPath()));
            // create a scene that will display the fxml parsed screen
            scene = new Scene(root);
            // set the default stylesheet to the current screen
            scene.getStylesheets().add(currentStyle.getPath());
            // put the scene in stage
            stage.setScene(scene);
            // disable the resizing ability
            stage.setResizable(false);
            // set the title of the app
            stage.setTitle(APP_NAME);
            // display the stage in the window
            stage.show();
            return true;
        } catch (Exception exception) {
            // Log possible errors that can appear when loading a FXML file 
            logger.log(SEVERE, "The app couldn't start correctly", exception);
            return false;
        }
    }

    /**
     * This method changes the current screen of the app based on the <b>layout</b>
     * parameter. This method enables the navigation to the manually built screen within the App.
     * 
     * @param layout - the layout pane that contains all the components
     */
    public static boolean display(Parent layout) {
        try {
            // create a scene that will display the manually built screen
            scene = new Scene(layout);
            // set the default stylesheet to the current screen
            scene.getStylesheets().add(currentStyle.getPath());
            // put the scene in stage
            stage.setScene(scene);
            // disable the resizing ability
            stage.setResizable(false);
            // set the title of the app
            stage.setTitle(APP_NAME);
            // display the stage in the window
            stage.show();
            return true;
        } catch (Exception exception) {
            // Log possible errors that can appear when setting the manually created parent
            logger.log(SEVERE, "The app couldn't start correctly", exception);
            return false;
        }
    }

    public static void main(String[] args) {
        // the application entry point
        launch(args);
    }

    /**
     * Method to switch the theme of the app. Currently the only two themes
     * supported by the app are White and Dark themes
     */
    public static void switchTheme() {
        if (currentStyle == Style.LIGHT)
            currentStyle = Style.DARK;
        else {
            currentStyle = Style.LIGHT;
        }
        // removes the old theme & inserters the newly selected theme
        scene.getStylesheets().remove(0);
        scene.getStylesheets().add(currentStyle.getPath());
    }

}
