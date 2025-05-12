module Asteroids {
    requires Common;
    requires CommonAsteroids;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.asteroids.AsteroidPlugin;
    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.asteroids.AsteroidProcessor,
                    dk.sdu.cbse.asteroids.AsteroidSpawner;
}
