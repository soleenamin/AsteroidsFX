module Player {
    exports dk.sdu.cbse.playersystem;
    requires Common;
    requires CommonBullet;
    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.bullet.BulletSPI;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.playersystem.PlayerPlugin;
    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.playersystem.PlayerControlSystem;
}
