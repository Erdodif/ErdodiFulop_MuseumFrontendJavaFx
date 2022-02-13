package hu.petrik.museumfrontendjavafx.statue;

import hu.petrik.museumfrontendjavafx.general.Controller;
import hu.petrik.museumfrontendjavafx.painting.Painting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddController extends Controller {
    public TextField personTextField;
    public Spinner<Integer> heightSpinner;
    public Spinner<Integer> priceSpinner;


    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
        String person = personTextField.getText();
        if (person.isEmpty()) {
            smallError("A cím megadása kötelező!");
            return;
        }
        try {
            int height = heightSpinner.getValue();
            int price = priceSpinner.getValue();
            boolean siker = new Statue(null, person, height, price).add() < 400;
            smallAlert("Sikeres hozzáadás!");
        } catch (NullPointerException e) {
            smallError("Mind a hossz, mind az ár megadása kötelező!");
        } catch (IOException e) {
            smallError("Szerver oldali hiba: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            smallError("Formátum hiba, mind a hossz,mind az ár egész számnak kell lennie.");
        }
    }

}
