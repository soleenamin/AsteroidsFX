package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * Runs once per frame after all IEntityProcessingService calls.
 * Use this for collisions, removing off-screen stuff, splitting asteroids, etc.
 */
public interface IPostEntityProcessingService {

    /**
     * Handle collisions and cleanup after movement.
     *
     * @param gameData current info (keys, screen size)
     * @param world contains all the entities
     */
    void process(GameData gameData, World world);
}
