package hu.petrik.museumfrontendjavafx.general;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class HelloController extends Controller {
    public void toStatues(ActionEvent actionEvent) {
        try {
            Controller newWindow = newWindow("statues.statue-main-view.fxml", "Új film", 600, 200);
            newWindow.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    //TODO
                }
            });
            newWindow.getStage().show();
            this.stage.close();
        } catch (IOException e) {
            exceptionAlert(e);
        }
    }

    public void toPaintings(ActionEvent actionEvent) {
        try {
            Controller newWindow = newWindow("paintings.painting-main-view.fxml", "Új film", 600, 200);
            newWindow.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    //TODO
                }
            });
            newWindow.getStage().show();
            this.stage.close();
        } catch (IOException e) {
            exceptionAlert(e);
        }
    }
}