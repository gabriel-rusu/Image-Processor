package image.processing.engine.constants;

public enum AppScreen {
    HOME("/templates/home-screen.fxml"),
    INFO("/templates/info-screen.fxml"),
    GALLERY("/templates/gallery-screen.fxml"),
    IMAGE_ROTATOR("/templates/image-rotator.fxml");

    private String path;
    AppScreen(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
