module Bullet {
    requires Common;
    requires CommonBullet;

    uses dk.sdu.cbse.common.bullet.BulletSPI;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IGamePluginService;

    provides dk.sdu.cbse.common.bullet.BulletSPI
            with dk.sdu.cbse.bulletsystem.BulletControlSystem;
    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.bulletsystem.BulletControlSystem;
    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.bulletsystem.BulletPlugin;
}
