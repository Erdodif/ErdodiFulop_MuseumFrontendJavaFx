package hu.petrik.museumfrontendjavafx.painting;

import hu.petrik.museumfrontendjavafx.general.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

public class MainController extends Controller {
    public TableView<Painting> paintingTable;
    public TableColumn<Painting, String> colTitle;
    public TableColumn<Painting, Integer> colYear;
    public TableColumn<Painting, Boolean> colOnDisplay;

    public void initialize() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("year"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("on_display"));
        try {
            loadDB();
        } catch (Exception e) {
            smallError(e.getMessage());
        }
    }

    private void loadDB() {
        try {
            List<Painting> paintings = Painting.allFromServer();
            paintingTable.getItems().clear();
            for (Painting painging : paintings) {
                paintingTable.getItems().add(painging);
            }
        } catch (IOException e) {
            exceptionAlert(e);
        }
    }

    @FXML
    public void onHozzaadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller newWindow = newWindow("painting-add-view.fxml", "Új Festmény", 600, 200);
            newWindow.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    loadDB();
                }
            });
            newWindow.getStage().show();
        } catch (IOException e) {
            exceptionAlert(e);
        }
    }

    @FXML
    public void onModositasButtonClick(ActionEvent actionEvent) {
        int selectedIndex = paintingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            smallError("Nincs kiválasztva elem!");
            return;
        }
        Painting modositando = paintingTable.getSelectionModel().getSelectedItem();
        try {
            EditController newWindow = (EditController) newWindow(
                    "painting-edit-view.fxml", modositando.getTitle() + " Módosítása", 600, 200);

            newWindow.getStage().setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    loadDB();
                }
            });
            newWindow.setModositando(modositando);
            newWindow.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
            exceptionAlert(e);
        }
    }

    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = paintingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            smallError("Nincs kiválasztva festmény!");
            return;
        }
        Painting paintingToDelete = paintingTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos, hogy törölni szeretné az alábbi festményt?", "A festmény neve: " + paintingToDelete.getTitle())) {
            return;
        }
        try {
            if (Painting.deleteFromId(paintingToDelete.getId()) < 400) {
                smallAlert("Sikeres törlés");
                loadDB();
                return;
            }
            smallAlert("Sikertelen törlés");
        } catch (Exception e) {
            exceptionAlert(e);
        }
    }


    public void cancelSelect(ActionEvent actionEvent) {
        paintingTable.getSelectionModel().clearSelection();
    }
}