package dk.sdu.cbse.asteroids;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Random;

/**
 * spawns a new asteroid every 3 seconds at a random screen edge.
 */
public class AsteroidSpawner implements IEntityProcessingService {
    private static final long SPAWN_INTERVAL_MS = 3000;
    private final Random random = new Random();
    private long lastSpawn = System.currentTimeMillis();

    @Override
    public void process(GameData gameData, World world) {
        long now = System.currentTimeMillis();
        if (now - lastSpawn < SPAWN_INTERVAL_MS) {
            return;
        }
        lastSpawn = now;
        // create & position a new asteroid
        Asteroid a = new Asteroid();
        int size = random.nextInt(10) + 5;
        a.setRadius(size);
        a.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

        // pick a random edge to spawn on
        double w = gameData.getDisplayWidth();
        double h = gameData.getDisplayHeight();
        switch (random.nextInt(4)) {
            case 0: a.setX(random.nextDouble() * w); a.setY(0); break;             // top
            case 1: a.setX(random.nextDouble() * w); a.setY(h); break;             // bottom
            case 2: a.setX(0);                  a.setY(random.nextDouble() * h); break; // left
            default:a.setX(w);                  a.setY(random.nextDouble() * h);     // right
        }
        a.setRotation(random.nextInt(360));
        world.addEntity(a);
    }
}
