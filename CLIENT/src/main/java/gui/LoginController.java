package gui;

import client.Client;
import client.ClientRMI;
import client.ClientTCP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Client client;
    private GuiApplication gui;
    @FXML
    private TextField username;
    @FXML
    private TextField serverIP;
    @FXML
    private RadioButton rmi;
    @FXML
    private Button signIn;

    public void init(GuiApplication gui) {
        this.gui = gui;
        username.textProperty().addListener((ov, oldValue, newValue) -> {
            signIn.setDisable(newValue.isEmpty());
        });
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException, InterruptedException {
        if (rmi.isSelected())
            client = new ClientRMI(null);
        else
            client = new ClientTCP(null);
        client.createUser(username.getText());
        ansToLogin(1);
    }

    public void ansToLogin(int answer) throws IOException {
        if (answer == 1) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lobbies.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LobbiesController lobbiesController = fxmlLoader.getController();
            client.getData().setGui(true);
            lobbiesController.setClient(client);
            lobbiesController.init(gui);
            Stage thisStage = (Stage) username.getScene().getWindow();
            thisStage.setScene(scene);
            thisStage.setTitle("Lobbies");
            thisStage.show();
            thisStage.centerOnScreen();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username invalid", ButtonType.CLOSE);
            alert.show();
        }
    }
}