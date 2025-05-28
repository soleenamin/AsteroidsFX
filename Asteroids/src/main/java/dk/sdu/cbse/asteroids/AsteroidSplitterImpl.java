package dk.sdu.cbse.asteroids;

import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

import java.util.Random;

/**
 * Splits a large asteroid into two smaller ones when it gets hit.
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {
    private static final Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        Asteroid parent = (Asteroid) e;
        float parentRadius = parent.getRadius();

        // only split if the rock is big enough
        if (parentRadius < 6) {
            // too small just destroy it without spawning children
            world.removeEntity(parent);
            return;
        }

        // compute child radius (half the parent’s size)
        float childRadius = parentRadius / 2f;

        // scale down the parent’s shape
        double[] coords = parent.getPolygonCoordinates();
        double[] newCoords = new double[coords.length];
        for (int i = 0; i < coords.length; i++) {
            newCoords[i] = coords[i] * 0.5;
        }

        // spawn two children
        for (int i = 0; i < 2; i++) {
            Asteroid child = new Asteroid();
            child.setRadius(childRadius);
            child.setPolygonCoordinates(newCoords);
            // start at the same position
            child.setX(parent.getX());
            child.setY(parent.getY());
            // give it a random spin
            child.setRotation(random.nextInt(360));
            world.addEntity(child);
        }

        // finally remove the original parent
        world.removeEntity(parent);
    }
}
