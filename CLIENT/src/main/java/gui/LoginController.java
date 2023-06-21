package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    Client client;
    @FXML
    private TextField username;
    @FXML
    private Text actionTarget;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        //client.createUser(username.getText());
        //If username is valid, go to lobbiesScreen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lobbies.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}
