package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

/**
 * Moves all bullets each frame, and
 * also implements the BulletSPI factory.
 */
public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity b : world.getEntities(Bullet.class)) {
            double dx = Math.cos(Math.toRadians(b.getRotation()));
            double dy = Math.sin(Math.toRadians(b.getRotation()));
            b.setX(b.getX() + dx * 5);
            b.setY(b.getY() + dy * 5);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        bullet.setPolygonCoordinates(1,-1, 1,1, -1,1, -1,-1);
        double dx = Math.cos(Math.toRadians(shooter.getRotation()));
        double dy = Math.sin(Math.toRadians(shooter.getRotation()));
        bullet.setX(shooter.getX() + dx * 20);
        bullet.setY(shooter.getY() + dy * 20);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);
        return bullet;
    }
}
