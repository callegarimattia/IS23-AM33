package gui;

import client.Client;
import client.ClientRMI;
import client.ClientTCP;
import javafx.concurrent.Task;
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

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        if (rmi.isSelected())
            client = new ClientRMI(null);
        else
            client = new ClientTCP(null);
        client.getData().setGui(true);
        client.getData().myUsernameProperty().addListener((obs, oldValue, newValue) -> {
            if (client.getData().getMyUsername().equals(username.getText())) openLobbiesScene();
        });
        client.createUser(username.getText());
        delay(500, this::checkUsernameError);
    }

    private void checkUsernameError() {
        if (client.getData().getMyUsername() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username Invalid", ButtonType.CLOSE);
            alert.show();
            username.clear();
        }
    }

    private void openLobbiesScene() {
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
    }
}