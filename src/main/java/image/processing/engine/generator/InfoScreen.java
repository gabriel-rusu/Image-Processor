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
    private static final String TOGGLE = "TOGGLE";
    private static final String SIMPLE = "SIMPLE";
    private static final String LOGO_IMAGE = "/templates/images/info.png";

    private InfoScreen() {
    }

    public static Parent build() {
        // Create the anchor pane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(600.0);
        anchorPane.setPrefWidth(900.0);

        ImageView logo = buildLogo(LOGO_IMAGE);

        Button backButton = (Button) createButton("Back", 20.0, 13.0, SIMPLE);
        backButton.setOnAction(event -> {
            MainController.display(AppScreen.HOME);
        });

        ToggleButton toggleButton = (ToggleButton) createButton("Dark", 844.0, 13.0, TOGGLE);
        toggleButton.setOnMouseClicked(event -> {
            System.out.println("clicked on the toggle button" + anchorPane.getStyle());
            MainController.switchTheme();
        });

        Label appNameLabel = buildLabel("App Name:", 349.0, 242.0);
        Label appVersionLabel = buildLabel("App Version:", 345.0, 268.0);
        Label lastUpdateLabel = buildLabel("Last Update:", 346.0, 291.0);
        Text appNameText = buildText("image-processor", 429.0, 255.0, StrokeType.OUTSIDE, 0.0);
        Text appVersionText = buildText("1.0.12", 429.0, 281.0, StrokeType.OUTSIDE, 0.0);
        Text appUpdateText = buildText("10/04/2021", 429.0, 305.0, StrokeType.OUTSIDE, 0.0);
        Hyperlink addressHyperlink = buildHyperLink("gabrielrusu.dev", 405.0, 562.0);

        anchorPane.getChildren().addAll(logo, backButton, toggleButton, appNameLabel, appNameText, appVersionLabel,
                appVersionText, lastUpdateLabel, appUpdateText, addressHyperlink);

        return anchorPane;
    }

    private static Hyperlink buildHyperLink(String text, Double layoutX, Double layoutY) {
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setLayoutX(layoutX);
        hyperlink.setLayoutY(layoutY);
        return hyperlink;
    }

    private static ImageView buildLogo(String path) {
        // Load the logo image
        Image image = new Image(path);

        // Place the logo image into an image view
        ImageView logo = new ImageView(image);
        logo.setFitWidth(175.0);
        logo.setFitHeight(101.0);
        logo.setLayoutX(214.0);
        logo.setLayoutY(226.0);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);
        return logo;
    }

    private static Node createButton(String text, Double layoutX, Double layoutY, String type) {
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

    private static Label buildLabel(String text, Double layoutX, Double layoutY) {
        Label label = new Label(text);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        return label;
    }

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
