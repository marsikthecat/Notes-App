module com.example.notelist {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.notelist to javafx.fxml, com.google.gson;
    exports com.example.notelist;
    exports com.example.notelist.ui;
    opens com.example.notelist.ui to javafx.fxml, com.google.gson;
    exports com.example.notelist.model;
    opens com.example.notelist.model to javafx.fxml, com.google.gson;
}