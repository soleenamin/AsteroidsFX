package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

/**
 * Cleans up bullets when the game stops.
 */
public class BulletPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // nothing to spawn at start
    }

    @Override
    public void stop(GameData gameData, World world) {
        // remove ALL bullets
        world.getEntities(Bullet.class)
                .forEach(world::removeEntity);
    }
}
