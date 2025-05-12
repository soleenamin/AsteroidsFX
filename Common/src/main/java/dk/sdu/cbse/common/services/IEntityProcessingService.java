package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * Runs every frame to update the entities (move them, shoot etc.).
 */

public interface IEntityProcessingService {

    /**
     * Update the entities once per frame.
     *
     * @param gameData current info (keys, screen size)
     * @param world contains all the entities.
     */
    void process(GameData gameData, World world);
}
