package dk.sdu.cbse.core;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;

import dk.sdu.cbse.playersystem.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;
import dk.sdu.cbse.enemysystem.Enemy;
import javafx.scene.paint.Color;
import dk.sdu.cbse.mapsystem.Background;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class App extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private Text destroyedText;
    private List<IEntityProcessingService> entityProcessors;
    private List<IPostEntityProcessingService> postProcessors;
    private ImageView backgroundView;


    public static void main(String[] args) {
        launch(App.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        // initialize the counter text as a field instead
        destroyedText = new Text(10, 20, "Destroyed asteroids: 0");
        destroyedText.setFill(Color.LIGHTYELLOW);

        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(destroyedText);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

        // lookup all game plugins using serviceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }

        // load & show the background image
        world.getEntities(Background.class).stream().findFirst().ifPresent(e -> {
            String url = ((Background)e).getImagePath();
            Image img = new Image(url);
            backgroundView = new ImageView(img);
            backgroundView.setFitWidth(gameData.getDisplayWidth());
            backgroundView.setFitHeight(gameData.getDisplayHeight());
            // add it *behind* everything else
            gameWindow.getChildren().add(0, backgroundView);
        });

        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        // load & cache all IEntityProcessingService implementations once
        entityProcessors = ServiceLoader
                .load(IEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());

// load & cache all IPostEntityProcessingService implementations once
        postProcessors = ServiceLoader
                .load(IPostEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());

        render();
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    private void render() {
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
        // use the same service instances every frame (so they keep their internal state!)
        for (IEntityProcessingService s : entityProcessors) {
            s.process(gameData, world);
        }
        for (IPostEntityProcessingService s : postProcessors) {
            s.process(gameData, world);
        }
    }


    private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if(!world.getEntities().contains(polygonEntity)){
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }

            // Color only enemy ship red
            if (entity instanceof Enemy) {
                polygon.setFill(Color.RED);
             }

            // Color Asteroids grey
            if (entity instanceof Asteroid) {
                polygon.setFill(Color.LIGHTGREY);
            }

            if (entity instanceof Player) {
                polygon.setFill(Color.LIGHTPINK);
            }

            if (entity instanceof Bullet) {
                polygon.setFill(Color.GRAY);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
     //update the counter when player destroys an asteroid
       destroyedText.setText("Destroyed asteroids: " + gameData.getDestroyedAsteroids());

    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
