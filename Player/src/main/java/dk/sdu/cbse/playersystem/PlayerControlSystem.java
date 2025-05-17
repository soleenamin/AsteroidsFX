package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.ServiceLoader;

/** Rotates/moves the player and fires bullets on SPACE. */
public class PlayerControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities(Player.class)) {
            // rotation
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                e.setRotation(e.getRotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                e.setRotation(e.getRotation() + 5);
            }

            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double dx = Math.cos(Math.toRadians(e.getRotation()));
                double dy = Math.sin(Math.toRadians(e.getRotation()));
                e.setX(e.getX() + dx);
                e.setY(e.getY() + dy);
            }

            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                ServiceLoader
                        .load(BulletSPI.class)
                        .findFirst()
                        .ifPresent(spi -> {
                            Entity b = spi.createBullet(e, gameData);
                            world.addEntity(b);
                        });
            }

            wrap(e, gameData);
        }
    }

    private void wrap(Entity e, GameData gd) {
        if (e.getX() < 0) e.setX(gd.getDisplayWidth());
        if (e.getX() > gd.getDisplayWidth()) e.setX(0);
        if (e.getY() < 0) e.setY(gd.getDisplayHeight());
        if (e.getY() > gd.getDisplayHeight()) e.setY(0);
    }
}
