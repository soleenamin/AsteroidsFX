package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * This is a plugin entry point for the game.
 *
 * start() is called once when the game begins so the plugin can
 * add the entities (ships, bullets, etc.) to the world.
 * stop() is called once when the game ends and cleans up.
 */
public interface IGamePluginService {
    /**
     * Add entities to the world before the game loop starts.
     *
     * @param gameData holds screen size and key states
     * @param world is where the entities are registered
     */
    void start(GameData gameData, World world);
    /**
     * Remove entities from the world after the game loop ends.
     *
     * @param gameData holds screen size and key states
     * @param world where the entities currently live
     */
    void stop(GameData gameData, World world);
}
