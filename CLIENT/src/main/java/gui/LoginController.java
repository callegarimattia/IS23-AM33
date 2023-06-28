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
    private Stage mainStage;
    private GuiApplication gui;
    @FXML
    private TextField username;
    @FXML
    private TextField serverIP;
    @FXML
    private RadioButton rmi;
    @FXML
    private Button signIn;

    public void init(GuiApplication gui, Stage mainStage) {
        this.gui = gui;
        this.mainStage = mainStage;
        username.textProperty().addListener((ov, oldValue, newValue) ->
                signIn.setDisable(newValue.isEmpty()));
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException, InterruptedException {
        if (rmi.isSelected())
            client = new ClientRMI(null);
        else
            client = new ClientTCP(null);
        client.getData().setGui(true);
        client.getData().usernameProperty().addListener((ov, oldValue, newValue) ->
                System.out.println("test"));
        client.createUser(username.getText());
        ansToLogin(username.getText());
    }

    public void ansToLogin(String username) {
        if (username.equals(this.username.getText())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lobbies.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LobbiesController lobbiesController = fxmlLoader.getController();
            lobbiesController.setClient(client);
            lobbiesController.init(gui);
            mainStage.setScene(scene);
            mainStage.setTitle("Lobbies");
            mainStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username invalid", ButtonType.CLOSE);
            alert.show();
        }
    }
}