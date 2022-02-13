package hu.petrik.museumfrontendjavafx.painting;

import hu.petrik.museumfrontendjavafx.general.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditController extends Controller {
    public Spinner<Integer> yearSpinner;
    public TextField titleTextField;
    public CheckBox onDisplayCheckBox;
    private Painting modositando;

    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent) {
        String title = titleTextField.getText();
        if (title.isEmpty()) {
            smallError("A név megadása kötelező!");
            return;
        }
        try {
            Integer year = yearSpinner.getValue();
            modositando.setTitle(title);
            modositando.setOnDisplay(onDisplayCheckBox.isSelected());
            modositando.setYear(year);
            int code = modositando.alter();
            smallAlert("Sikeres módosítás!");
            this.stage.close();
        } catch (NullPointerException e) {
            smallError("Az év megadása kötelező!");
        } catch (IOException e) {
            smallError("Szerver oldali hiba: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            smallError("Az ár egy 300 és 300000 közötti egész számnak kell lennie!");
        }
    }

    public void setModositando(Painting modositando) {
        this.modositando = modositando;
        titleTextField.setText(modositando.getTitle());
        yearSpinner.getEditor().setText(Integer.toString(modositando.getYear()));
        onDisplayCheckBox.setSelected(modositando.isOnDisplay());
    }

    public Painting getModositando() {
        return this.modositando;
    }
}
