module Map {
    requires Common;
    requires CommonMap;
    requires javafx.graphics;

    uses    dk.sdu.cbse.common.services.IGamePluginService;
    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.mapsystem.MapPlugin;

    uses    dk.sdu.cbse.common.map.MapSPI;
    provides dk.sdu.cbse.common.map.MapSPI
            with dk.sdu.cbse.mapsystem.MapPlugin;

    exports dk.sdu.cbse.mapsystem;
}
