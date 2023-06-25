module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires java.rmi;
    requires json.simple;

    opens gui to javafx.fxml;
    exports gui;
    exports client;
    exports common;
    exports client.clientModel;
    opens client to javafx.fxml;
}