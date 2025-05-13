package dk.sdu.cbse.mapsystem;

import dk.sdu.cbse.common.data.Entity;

/**
 * Carries the path (or URL) for a background image.
 */
public class Background extends Entity {
    private final String imagePath;
    public Background(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }
}
