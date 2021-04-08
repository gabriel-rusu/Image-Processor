package image.processing.engine;

public enum Rotate {
    DEGREES_90(90),
    DEGREES_180(180),
    DEGREES_270(270);

    Integer value;

    Rotate(int value) {
        this.value = value;
    }

    int valueOf() {
        return this.value;
    }
    
}
