module com.example.notelist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.notelist to javafx.fxml;
    exports com.example.notelist;
    exports com.example.notelist.ui;
    opens com.example.notelist.ui to javafx.fxml;
    exports com.example.notelist.model;
    opens com.example.notelist.model to javafx.fxml;
}