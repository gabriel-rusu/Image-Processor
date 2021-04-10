package image.processing.engine.constants;

public enum AppScreen {
    HOME("HOME");

    private String path;
    AppScreen(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
