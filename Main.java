package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new MenuScreen(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args); // Lance JavaFX
    }
}

