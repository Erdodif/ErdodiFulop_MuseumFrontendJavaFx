package hu.petrik.museumfrontendjavafx.statue;

import hu.petrik.museumfrontendjavafx.general.Controller;
import hu.petrik.museumfrontendjavafx.painting.Painting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

public class MainController extends Controller {
    public TableView<Statue> paintingTable;
    public TableColumn<Statue,String> colPerson;
    public TableColumn<Statue,Integer> colHeight;
    public TableColumn<Statue,Boolean> colPrice;

    public void initialize(){
        colPerson.setCellValueFactory(new PropertyValueFactory<>("person"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
            loadDB();
        } catch (Exception e) {
            smallError(e.getMessage());
        }
    }

    private void loadDB() {
        try {
            List<Statue> statues = Statue.allFromServer();
            paintingTable.getItems().clear();
            for (Statue statue: statues) {
                paintingTable.getItems().add(statue);
            }
        } catch (IOException e) {
            exceptionAlert(e);
        }
    }

    @FXML
    public void onHozzaadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller newWindow = newWindow("statue-add-view.fxml", "Új Szobor", 600, 200);
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
        Statue modositando = paintingTable.getSelectionModel().getSelectedItem();
        try {
            EditController newWindow = (EditController) newWindow(
                    "statue-edit-view.fxml", modositando.getPerson() + " Módosítása", 600, 200);

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
        Statue statueToDelete = paintingTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos, hogy törölni szeretné az alábbi Szobrot?", "A szobor múzsája: " + statueToDelete.getPerson())) {
            return;
        }
        try {
            if (Statue.deleteFromId(statueToDelete.getId()) < 400) {
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