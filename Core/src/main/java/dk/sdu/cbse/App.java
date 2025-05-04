package dk.sdu.cbse;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AsteroidsFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
