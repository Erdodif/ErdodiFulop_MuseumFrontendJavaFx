package hu.petrik.museumfrontendjavafx.painting;

import hu.petrik.museumfrontendjavafx.general.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddController extends Controller {
    public TextField titleTextField;
    public Spinner<Integer> yearSpinner;
    public CheckBox onDisplayCheckBox;

    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
        String title = titleTextField.getText();
        boolean onDisplay = onDisplayCheckBox.isSelected();
        if (title.isEmpty()) {
            smallError("A cím megadása kötelező!");
            return;
        }
        try {
            int year = yearSpinner.getValue();
            boolean siker = new Painting(null, title, year, onDisplay).add() < 400;
            smallAlert("Sikeres hozzáadás!");
        } catch (NullPointerException e) {
            smallError("Az év megadása kötelező!");
        } catch (IOException e) {
            smallError("Szerver oldali hiba: " + e.getMessage());
        } catch (Exception e) {
            smallError("Az év egy 0 és 3000 közötti egész számnak kell lennie!");
        }
    }

}
