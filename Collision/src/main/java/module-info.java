import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;

    provides IPostEntityProcessingService with dk.sdu.cbse.collisionsystem.CollisionDetector;
}