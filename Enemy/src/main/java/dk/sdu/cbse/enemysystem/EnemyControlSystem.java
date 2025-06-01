package dk.sdu.cbse.enemysystem;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Random;
import java.util.ServiceLoader;

/**
 * Moves each enemy randomly and fires a bullet occasionally.
 */
public class EnemyControlSystem implements IEntityProcessingService {
    private final Random rnd = new Random();
    private static final double SPEED = 1.0;
    private static final double ROTATION_VARIANCE = 1.0;


    @Override
    public void process(GameData gameData, World world) {
        for (Entity en : world.getEntities(Enemy.class)) {

            double rot = en.getRotation() + (rnd.nextDouble() - 0.5) * ROTATION_VARIANCE;
            en.setRotation(rot);

            // thrust forward
            double dx = Math.cos(Math.toRadians(rot));
            double dy = Math.sin(Math.toRadians(rot));
            en.setX(en.getX() + dx * SPEED);
            en.setY(en.getY() + dy * SPEED);

            // wrap around
            if (en.getX() < 0) en.setX(gameData.getDisplayWidth());
            if (en.getX() > gameData.getDisplayWidth()) en.setX(0);
            if (en.getY() < 0) en.setY(gameData.getDisplayHeight());
            if (en.getY() > gameData.getDisplayHeight()) en.setY(0);

            // random fire (~2% chance per frame)
            if (rnd.nextDouble() < 0.02) {
                ServiceLoader.load(BulletSPI.class)
                        .stream()
                        .map(ServiceLoader.Provider::get)
                        .findFirst()
                        .ifPresent(spi -> world.addEntity(
                                spi.createBullet(en, gameData)

                        ));
            }
        }
    }
}
