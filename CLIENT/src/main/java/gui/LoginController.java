package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginController {

    Client client;
    GuiApplication gui;
    @FXML
    private TextField username;
    @FXML
    private Text actionTarget;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setGui(GuiApplication gui) {
        this.gui = gui;
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        client.createUser(username.getText());
        gui.ansToLogin(1);
    }
}
