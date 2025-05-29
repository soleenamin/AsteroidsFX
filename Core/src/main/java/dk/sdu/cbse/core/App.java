// App.java
package dk.sdu.cbse.core;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.*;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.player.Player;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.map.MapSPI;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class App {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private Text destroyedText;
    private ImageView backgroundView;

    private final List<IGamePluginService> pluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    public App(
            List<IGamePluginService> pluginServices,
            List<IEntityProcessingService> entityProcessingServices,
            List<IPostEntityProcessingService> postEntityProcessingServices
    ) {
        this.pluginServices = pluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) {
        // Counter text
        destroyedText = new Text(10, 20, "Destroyed asteroids: 0");
        destroyedText.setFill(Color.LIGHTYELLOW);

        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(destroyedText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(e -> handleKey(e.getCode(), true));
        scene.setOnKeyReleased(e -> handleKey(e.getCode(), false));

        // start all plugins
        pluginServices.forEach(p -> p.start(gameData, world));

        // load a background map if available
        ServiceLoader.load(MapSPI.class)
                .findFirst()
                .ifPresent(m -> {
                    backgroundView = m.getMap();
                    backgroundView.setFitWidth(gameData.getDisplayWidth());
                    backgroundView.setFitHeight(gameData.getDisplayHeight());
                    gameWindow.getChildren().add(0, backgroundView);
                });

        // create initial polygons
        world.getEntities().forEach(e -> {
            Polygon poly = new Polygon(e.getPolygonCoordinates());
            polygons.put(e, poly);
            gameWindow.getChildren().add(poly);
        });

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();

        render();
    }

    private void handleKey(KeyCode code, boolean pressed) {
        switch (code) {
            case LEFT  -> gameData.getKeys().setKey(GameKeys.LEFT, pressed);
            case RIGHT -> gameData.getKeys().setKey(GameKeys.RIGHT, pressed);
            case UP    -> gameData.getKeys().setKey(GameKeys.UP, pressed);
            case SPACE -> gameData.getKeys().setKey(GameKeys.SPACE, pressed);
        }
    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        entityProcessingServices.forEach(s -> s.process(gameData, world));
        postEntityProcessingServices.forEach(s -> s.process(gameData, world));
    }

    private void draw() {
        // remove dead entities
        polygons.keySet().removeIf(e -> {
            if (!world.getEntities().contains(e)) {
                gameWindow.getChildren().remove(polygons.get(e));
                return true;
            }
            return false;
        });

        // update or create polygons
        world.getEntities().forEach(e -> {
            Polygon poly = polygons.computeIfAbsent(e, k -> {
                Polygon p = new Polygon(k.getPolygonCoordinates());
                gameWindow.getChildren().add(p);
                return p;
            });

            poly.setTranslateX(e.getX());
            poly.setTranslateY(e.getY());
            poly.setRotate(e.getRotation());

            if      (e instanceof Enemy)   poly.setFill(Color.RED);
            else if (e instanceof Asteroid) poly.setFill(Color.LIGHTGREY);
            else if (e instanceof Player)   poly.setFill(Color.LIGHTPINK);
            else if (e instanceof Bullet)   poly.setFill(Color.GRAY);
        });

        destroyedText.setText("Destroyed asteroids: " + gameData.getDestroyedAsteroids());
    }
}
