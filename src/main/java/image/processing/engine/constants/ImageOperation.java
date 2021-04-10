package image.processing.engine.constants;

public enum ImageOperation {
    ROTATE(0),
    GRAY_SCALE(1);

    private int value;

    ImageOperation(int value) {
        this.value = value;
    }

    int valueOf() {
        return this.value;
    }
}
