module Map {
    requires Common;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.mapsystem.MapPlugin;

    exports dk.sdu.cbse.mapsystem;
}
