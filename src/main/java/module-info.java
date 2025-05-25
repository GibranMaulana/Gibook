module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.core;
    requires javafx.base;

    opens com.example to javafx.fxml;
    opens com.example.ui to javafx.fxml;
    opens com.example.data to javafx.fxml, com.fasterxml.jackson.databind, com.fasterxml.jackson.datatype.jsr310;
    opens com.example.model to javafx.fxml, com.fasterxml.jackson.databind, com.fasterxml.jackson.datatype.jsr310;
    
    exports com.example;
    exports com.example.ui;
    exports com.example.data;
    exports com.example.model;
}
