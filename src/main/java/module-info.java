module hu.petrik.museumfrontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens hu.petrik.museumfrontendjavafx to javafx.fxml;
    exports hu.petrik.museumfrontendjavafx.general;
    opens hu.petrik.museumfrontendjavafx.general to javafx.fxml;
}