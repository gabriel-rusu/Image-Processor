package image.processing.engine.constants;

public enum Style {
    DARK("/templates/styles/dark-mode.css"),
    LIGHT("/templates/styles/light-mode.css");

    private String path;

    Style(String path) {
        this.path = path;  
    }

    public String getPath() {
        return path;
    }
    
}
