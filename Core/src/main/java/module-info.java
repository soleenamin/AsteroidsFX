module Core {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    requires javafx.controls;
    requires CommonAsteroids;
    requires CommonEnemy;
    requires CommonPlayer;
    requires CommonMap;

    opens dk.sdu.cbse.core to javafx.graphics, javafx.fxml;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;
    uses dk.sdu.cbse.common.map.MapSPI;
}
