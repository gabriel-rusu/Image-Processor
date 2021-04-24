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
    private ListView<String> imagesList;

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.logger = Logger.getLogger(GalleryController.class.getName());
        this.logger.info("Initializing the GalleryController.class");
        try {
            URI uri = getClass().getClassLoader().getResource("images").toURI();
            this.imageDirectory = new File(Paths.get(uri).toString());
            this.images = imageDirectory.listFiles(file -> file.isFile());

            for (File image : images) {
                imagesList.getItems().add(image.getName());
            }
            imagesList.setEditable(false);
            imagesList.setOnMouseReleased(this::displaySelectedImage);
            imageView.setImage(imageOfFile(images[0]));
            
        } catch (Exception exception) {
            this.logger.log(SEVERE, "Something went wrong opening the directory 'images' ", exception);
        }

    }

    private void displaySelectedImage(MouseEvent event) {
        String selectedImage = imagesList.getSelectionModel().getSelectedItem();
        if(selectedImage == null)
            return;
        for (File image : images) {
            if (image.getName().equals(selectedImage)) {
                imageView.setImage(imageOfFile(image));
            }
        }
    }

    private Image imageOfFile(File image) {
        try (FileInputStream inputStream = new FileInputStream(image)) {
            return new Image(new FileInputStream(image));
        } catch (Exception exception) {
            logger.log(WARNING ,"Couldn't open the image " + image.getName(), exception);
        }
        return null;
    }

    @FXML
    public void goBack() {
        MainController.display(AppScreen.HOME);
    }

    @FXML
    public void switchTheme() {
        MainController.switchTheme();
    }

}
