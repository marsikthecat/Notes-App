module com.example.notelist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.notelist to javafx.fxml;
    exports com.example.notelist;
}