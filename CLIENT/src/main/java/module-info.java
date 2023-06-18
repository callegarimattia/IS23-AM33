module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;

    opens gui to javafx.fxml;
    exports gui;
}