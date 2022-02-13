package hu.petrik.museumfrontendjavafx.statue;

import hu.petrik.museumfrontendjavafx.general.Controller;
import hu.petrik.museumfrontendjavafx.painting.Painting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditController extends Controller {
    public TextField personTextField;
    public Spinner<Integer> heightSpinner;
    public Spinner<Integer> priceSpinner;
    private Statue modositando;

    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent) {
        String person = personTextField.getText();
        if (person.isEmpty()) {
            smallError("A múzsa megadása kötelező!");
            return;
        }
        try {
            Integer height = heightSpinner.getValue();
            Integer price = priceSpinner.getValue();
            modositando.setPerson(person);
            modositando.setHeight(height);
            modositando.setPrice(price);
            int code = modositando.alter();
            smallAlert("Sikeres módosítás!");
            this.stage.close();
        } catch (NullPointerException e) {
            smallError("Mind a hossz, mind az ár megadása kötelező!");
        } catch (IOException e) {
            smallError("Szerver oldali hiba: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            smallError("Formátum hiba, mind a hossz,mind az ár egész számnak kell lennie.");
        }
    }

    public void setModositando(Statue modositando) {
        this.modositando = modositando;
        personTextField.setText(modositando.getPerson());
        heightSpinner.getEditor().setText(Integer.toString(modositando.getHeight()));
        priceSpinner.getEditor().setText(Integer.toString(modositando.getPrice()));
    }

    public Statue getModositando() {
        return this.modositando;
    }
}
