package dk.sdu.cbse.mapsystem;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.map.MapSPI;
import dk.sdu.cbse.common.services.IGamePluginService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class MapPlugin implements IGamePluginService, MapSPI {

    private static ImageView mapView;

    @Override
    public void start(GameData gameData, World world) {
        // load once on startup
        URL url = getClass().getResource("/galaxy.jpg");
        if (url != null) {
            Image img = new Image(url.toString());
            mapView = new ImageView(img);
            mapView.setFitWidth(gameData.getDisplayWidth());
            mapView.setFitHeight(gameData.getDisplayHeight());
        } else {
            System.err.println("No map image found!");
        }
    }

    @Override
    public ImageView getMap() {
        return mapView;
    }

    @Override
    public void stop(GameData gameData, World world) {
        mapView = null;
    }
}
