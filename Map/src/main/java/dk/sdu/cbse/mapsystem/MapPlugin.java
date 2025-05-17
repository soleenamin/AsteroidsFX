package dk.sdu.cbse.mapsystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

/**
 * On game start, makes a Background entity referencing the image file.
 */
public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // load galaxy.jpg from the classpath
        String path = getClass()
                .getClassLoader()
                .getResource("galaxy.jpg")
                .toExternalForm();

        Background bg = new Background(path);
        bg.setX(0);
        bg.setY(0);
        world.addEntity(bg);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Background.class)
                .forEach(world::removeEntity);
    }
}
