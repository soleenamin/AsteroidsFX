package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;

/**
 *  for creating bullet entities.
 */
public interface BulletSPI {
    /**
     * Build a new bullet based on its shooter’s state.
     *
     * @param shooter the entity that fires (not null)
     * @param gameData current game info (not null)
     * @return a fresh Bullet entity
     */
    Entity createBullet(Entity shooter, GameData gameData);
}
