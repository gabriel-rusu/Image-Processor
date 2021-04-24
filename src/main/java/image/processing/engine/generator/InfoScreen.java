package image.processing.engine.generator;

import image.processing.engine.constants.AppScreen;
import image.processing.engine.controllers.MainController;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class InfoScreen {
    // info screen specific constants
    private static final String TOGGLE = "TOGGLE";
    private static final String SIMPLE = "SIMPLE";
    private static final String LOGO_IMAGE = "/templates/images/info.png";

    /**
     * Private constructor to force the user to use the build static method.
     */
    private InfoScreen() {
    }

    /**
     * Method that constructs a screen withe a pane of type AnchorPane, the screen
     * displays some useful information about the app such as last update, version
     * and name.
     * 
     * 
     * @return the newly created screen
     */
    public static Parent build() {
        // Create the anchor pane & set the needed dimensions
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(600.0);
        anchorPane.setPrefWidth(900.0);

        // create the ImageView from the path of the logo image
        ImageView logo = buildLogo(LOGO_IMAGE);

        // creates a normal button with the help of the buttonFactory
        Button backButton = (Button) buttonFactory("Back", 20.0, 13.0, SIMPLE);
        // adds an event handler lambda implementation to go to the home screen of the app when the button is clicked
        backButton.setOnAction(event -> {
            MainController.display(AppScreen.HOME);
        });

        // creates a normal button with the help of the buttonFactory
        ToggleButton toggleButton = (ToggleButton) buttonFactory("Switch Theme", 796.0, 13.0, TOGGLE);
        // adds an event handler lambda implementation to switch the theme of the app when the button is toggled/untoggled
        toggleButton.setOnMouseClicked(event -> {
            MainController.switchTheme();
        });

        // this section creates the labels in the info screen
        Label appNameLabel = buildLabel("App Name:", 349.0, 242.0);
        Label appVersionLabel = buildLabel("App Version:", 345.0, 268.0);
        Label lastUpdateLabel = buildLabel("Last Update:", 346.0, 291.0);

        // this section creates the text components in the info screen
        Text appNameText = buildText("image-processor", 429.0, 255.0, StrokeType.OUTSIDE, 0.0);
        Text appVersionText = buildText("1.0.0", 429.0, 281.0, StrokeType.OUTSIDE, 0.0);
        Text appUpdateText = buildText("24/04/2021", 429.0, 305.0, StrokeType.OUTSIDE, 0.0);
        Hyperlink addressHyperlink = buildHyperLink("gabrielrusu.dev", 405.0, 574.0);

        // add all the node created before to the anchorPane for rendering
        anchorPane.getChildren().addAll(logo, backButton, toggleButton, appNameLabel, appNameText, appVersionLabel,
                appVersionText, lastUpdateLabel, appUpdateText, addressHyperlink);

        return anchorPane;
    }

    /**
     * Helper method that creates a hyperlink with the properties specified as
     * parameters.
     * 
     * @param text    - the text that the hyperlink will display
     * @param layoutX - the layout X coordinate (used for positioning in the screen)
     * @param layoutY - the layout Y coordinate (used for positioning in the screen)
     * @return the newly created Hyperlink object
     */
    private static Hyperlink buildHyperLink(String text, Double layoutX, Double layoutY) {
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setLayoutX(layoutX);
        hyperlink.setLayoutY(layoutY);
        return hyperlink;
    }

    /**
     * Helper method that creates the ImageView of the info screen logo.
     * 
     * @param path the path of the image that will be displayed as logo
     * @return - the newly created ImageView
     */
    private static ImageView buildLogo(String path) {
        // Load the logo image
        Image image = new Image(path);

        // Place the logo image into an image view & set the dimensions/ rendering options
        ImageView logo = new ImageView(image);
        logo.setFitWidth(175.0);
        logo.setFitHeight(101.0);
        logo.setLayoutX(214.0);
        logo.setLayoutY(226.0);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);
        return logo;
    }

    /**
     * A factory method that creates a button with the properties specified as
     * parameters, the type parameter will control the type of the object being
     * created and returned
     * 
     * @param text    - the text that the button will display
     * @param layoutX - the layout X coordinate (used for positioning in the screen)
     * @param layoutY - the layout Y coordinate (used for positioning in the screen)
     * @param type    - the type of the button that can be either ToggleButton or
     *                Button
     * @return the newly created button object
     */
    private static Node buttonFactory(String text, Double layoutX, Double layoutY, String type) {
        Node node;

        switch (type) {
        case TOGGLE:
            node = new ToggleButton(text);
            break;
        default:
            node = new Button(text);
        }
        node.setLayoutX(layoutX);
        node.setLayoutY(layoutY);

        return node;
    }

    /**
     * Helper method that creates a label with the properties specified as
     * parameters.
     * 
     * @param text    - the text that the label will display
     * @param layoutX - the layout X coordinate (used for positioning in the screen)
     * @param layoutY - the layout Y coordinate (used for positioning in the screen)
     * @return the newly created label object
     */
    private static Label buildLabel(String text, Double layoutX, Double layoutY) {
        Label label = new Label(text);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        return label;
    }

    /**
     * Helper method that creates a text node with the properties specified as
     * parameters.
     * 
     * @param text        - the text that the text node will display
     * @param layoutX     - the layout X coordinate (used for positioning in the screen)
     * @param layoutY     - the layout Y coordinate (used for positioning in the screen)
     * @param strokeType  - the stroke type that will be used for rendering the text
     * @param strokeWidth - the stroke width that the text will have
     * @return - the newly created textNode
     */
    private static Text buildText(String text, Double layoutX, Double layoutY, StrokeType strokeType,
            Double strokeWidth) {
        Text textNode = new Text(text);
        textNode.setLayoutX(layoutX);
        textNode.setLayoutY(layoutY);
        textNode.setStrokeType(strokeType);
        textNode.setStrokeWidth(strokeWidth);
        return textNode;
    }

}
