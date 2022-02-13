package hu.petrik.museumfrontendjavafx.general;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MuseumApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(MuseumApp.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(MuseumApp.class.getResource("/statue-add-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Múzeum lenni");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}