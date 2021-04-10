package image.processing.engine.constants;

public enum AppScreen {
    HOME("HOME"),
    INFO("/templates/info-screen.fxml");

    private String path;
    AppScreen(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
