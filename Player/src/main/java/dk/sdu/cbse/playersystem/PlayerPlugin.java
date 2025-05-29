package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

/**  Adds/removes the player ship at game start/stop. */
public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {
        Player p = new Player();
        // was (-5,-5,10,0,-5,5) / radius 8
        p.setPolygonCoordinates(-10, -10, 20, 0, -10, 10);
        p.setX(gameData.getDisplayWidth() / 2.0);
        p.setY(gameData.getDisplayHeight() / 2.0);
        p.setRadius(16);            // doubled
        return p;
    }


    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
