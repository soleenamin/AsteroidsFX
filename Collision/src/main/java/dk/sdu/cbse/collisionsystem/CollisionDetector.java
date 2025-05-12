package dk.sdu.cbse.collisionsystem;

import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.asteroids.Asteroid;


public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }
                // Collision detection
                if (this.collides(entity1, entity2)) {
                    if ((entity1 instanceof Bullet && entity2 instanceof Asteroid) ||
                            (entity2 instanceof Bullet && entity1 instanceof Asteroid)) {
                        gameData.incrementDestroyedAsteroids();
                    }
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
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
