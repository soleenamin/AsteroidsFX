package dk.sdu.cbse.asteroids;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

/** Moves asteroids and wraps them around the screen. */
public class AsteroidProcessor implements IEntityProcessingService {
    private IAsteroidSplitter splitter = new AsteroidSplitterImpl();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities(Asteroid.class)) {
            double dx = Math.cos(Math.toRadians(e.getRotation()));
            double dy = Math.sin(Math.toRadians(e.getRotation()));
            e.setX(e.getX() + dx * 0.5);
            e.setY(e.getY() + dy * 0.5);

            // wrap
            if (e.getX() < 0) e.setX(e.getX() + gameData.getDisplayWidth());
            if (e.getX() > gameData.getDisplayWidth())
                e.setX(e.getX() % gameData.getDisplayWidth());
            if (e.getY() < 0) e.setY(e.getY() + gameData.getDisplayHeight());
            if (e.getY() > gameData.getDisplayHeight())
                e.setY(e.getY() % gameData.getDisplayHeight());
        }
    }

    public void setAsteroidSplitter(IAsteroidSplitter splitter) {
        this.splitter = splitter;
    }
    public void removeAsteroidSplitter(IAsteroidSplitter splitter) {
        this.splitter = null;
    }
}
