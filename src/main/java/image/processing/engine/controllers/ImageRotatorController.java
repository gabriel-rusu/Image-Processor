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
    ImageView inputImage;

    @FXML
    ImageView outputImage;

    @FXML
    RadioButton degreeOption1;

    @FXML
    RadioButton degreeOption2;

    @FXML
    RadioButton degreeOption3;

    @FXML
    TextField targetFileName;

    @FXML
    ComboBox<String> options;

    private ToggleGroup group;
    private File selectedImage = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.logger = Logger.getLogger(ImageRotatorController.class.getName());
        this.group = new ToggleGroup();
        group.getToggles().addAll(degreeOption1, degreeOption2, degreeOption3);
        try {
            URI uri = getClass().getClassLoader().getResource("images").toURI();
            this.imageDirectory = new File(Paths.get(uri).toString());
            this.images = imageDirectory.listFiles(file -> file.isFile());

            for (File image : images) {
                options.getItems().add(image.getName());
            }
            options.setOnAction(this::displaySelected);

        } catch (Exception exception) {
            logger.log(SEVERE, "Something went wrong opening directory 'images' ", exception);
        }

    }

    private void displaySelected(ActionEvent selection) {
        String selectedOption = options.getSelectionModel().getSelectedItem();

        for (File image : this.images) {
            if (selectedOption.equals(image.getName())) {
                selectedImage = image;
            }
        }
        try (FileInputStream inputStream = new FileInputStream(selectedImage)) {
            inputImage.setImage(new Image(inputStream));
        } catch (Exception exception) {
            logger.log(SEVERE, "Something went wrong opening the selected image: " + selectedOption, exception);
        }
    }

    public void goBack() {
        MainController.display(AppScreen.HOME);
    }

    public void switchTheme() {
        MainController.switchTheme();
    }

    public void processImage() {
        String outputFileName;
        RadioButton selectedButton = (RadioButton) this.group.getSelectedToggle();


        if(selectedButton==null || selectedImage ==null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Parameters");
            String message = selectedImage==null ? "Please select an input image!" : "Please select how to rotate the image";
     
            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText(message);

     
            alert.showAndWait();
            return;
        }

        String selectedLabel = selectedButton.getText();
        String degrees = selectedLabel.substring(0, selectedLabel.length() - 1);


        if (this.targetFileName.getText().isEmpty())
            outputFileName = TEMP_BMP;
        else
            outputFileName = this.targetFileName.getText();

        image.processing.engine.models.Image.open(selectedImage.getAbsolutePath()).apply(ImageOperation.ROTATE, degrees)
                .saveTo(outputFileName);
        File result = new File(outputFileName);
        try (FileInputStream image = new FileInputStream(result)) {
            outputImage.setImage(new Image(image));
            
        } catch (Exception exception) {
            logger.log(SEVERE, "Could not save the image tmp.bmp!", exception);
        }finally {
            if(result.getName().equals(TEMP_BMP)) {
                this.logger.info("Deleted the temporary temp.bmp image!");
                result.delete();
            }
        }

    }

}
