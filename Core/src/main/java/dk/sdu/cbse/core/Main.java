package dk.sdu.cbse.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Bootstrap Spring context
        try (var ctx = new AnnotationConfigApplicationContext(ModuleConfig.class)) {
            App app = ctx.getBean(App.class);
            app.start(primaryStage);
            app.render();
        }
    }
}
