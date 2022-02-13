package hu.petrik.museumfrontendjavafx.general;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Controller {

    protected Stage stage;

    public Stage getStage(){
        return this.stage;
    }

    public void showAlert(Alert.AlertType type, String s, EventHandler<DialogEvent> handler) {
        Alert alert = new Alert(type);
        alert.setContentText(s);
        if (type == Alert.AlertType.NONE){
            alert.getButtonTypes().add(ButtonType.OK);
        }
        if(handler != null){
            alert.setOnCloseRequest(handler);
        }
        alert.show();
    }
    public void smallAlert(String s) {
        showAlert(Alert.AlertType.NONE,s,null);
    }
    public void smallError(String s) {
        showAlert(Alert.AlertType.ERROR,s,null);
    }
    public void exitAlert(String s){
        showAlert(Alert.AlertType.NONE, s, new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                //TODO
            }
        });
    }

    public void exceptionAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(alert::show);
            }
        },500);
    }

    public boolean confirm(String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("are you sure about it?");
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait().get().equals(ButtonType.OK);
    }

    public void alertWait(String content){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Controller newWindow(String fxml, String title,int width,int height) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MuseumApp.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;
        return controller;
    }
}