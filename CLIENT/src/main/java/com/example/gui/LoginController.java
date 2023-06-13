package com.example.gui;

import com.example.gui.client.clientModel.DataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private Button submitButton;
    @FXML
    private RadioButton rmi;
    @FXML
    private RadioButton tcp;
    @FXML
    private ToggleGroup connectionMode;
    private DataModel model;

    public void init() {

    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        if (username.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error!",
                    "Please enter your name");
            return;

        }
        if (rmi.isSelected() && !tcp.isSelected()) model.createClientRMI();
        else model.createClientTCP();

        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                "Welcome " + username.getText());

    }
}
