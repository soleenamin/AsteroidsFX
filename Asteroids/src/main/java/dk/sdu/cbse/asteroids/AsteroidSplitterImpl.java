package dk.sdu.cbse.asteroids;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {
    private static final Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        Asteroid parent = (Asteroid) e;
        float parentRadius = parent.getRadius();
        if (parentRadius <= 10) {
            return; //
        }
        for (int i = 0; i < 2; i++) {
            Asteroid child = new Asteroid();
            child.setRadius(parentRadius / 2);
            // Offset the children a bit in different directions
            double angle = Math.toRadians(random.nextInt(360));
            double offset = parentRadius / 2.0;
            child.setX(parent.getX() + Math.cos(angle) * offset);
            child.setY(parent.getY() + Math.sin(angle) * offset);
            // Give each child a random rotation (trajectory)
            child.setRotation((float) (random.nextDouble() * 360));
            // If you have a setSpeed or velocity, randomize it here too!
            world.addEntity(child);
            System.out.println("Child asteroid spawned at X=" + child.getX() + ", Y=" + child.getY() + ", radius=" + child.getRadius());
        }

    }
}

