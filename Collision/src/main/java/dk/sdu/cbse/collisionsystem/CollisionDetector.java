package dk.sdu.cbse.collisionsystem;

import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }
                if (this.collides(entity1, entity2)) {
                    boolean entity1IsAsteroid = entity1 instanceof Asteroid;
                    boolean entity2IsAsteroid = entity2 instanceof Asteroid;
                    boolean entity1IsBullet = entity1 instanceof Bullet;
                    boolean entity2IsBullet = entity2 instanceof Bullet;

                    if (entity1IsAsteroid && entity2IsAsteroid) {
                        for (IAsteroidSplitter splitter : ServiceLoader.load(IAsteroidSplitter.class)) {
                            splitter.createSplitAsteroid(entity1, world);
                            splitter.createSplitAsteroid(entity2, world);
                        }
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    } else if (entity1IsAsteroid && entity2IsBullet) {
                        for (IAsteroidSplitter splitter : ServiceLoader.load(IAsteroidSplitter.class)) {
                            splitter.createSplitAsteroid(entity1, world);
                        }
                        gameData.incrementDestroyedAsteroids();
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    } else if (entity2IsAsteroid && entity1IsBullet) {
                        for (IAsteroidSplitter splitter : ServiceLoader.load(IAsteroidSplitter.class)) {
                            splitter.createSplitAsteroid(entity2, world);
                        }
                        gameData.incrementDestroyedAsteroids();
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    } else {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
