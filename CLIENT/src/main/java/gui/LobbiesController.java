package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LobbiesController {
    Client client;

    GuiApplication gui;

    @FXML
    private Button refreshButton;


    public void init(GuiApplication gui) {
        this.gui = gui;
        client.lobbyListRequest();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleRefreshAction(ActionEvent event) {
        client.lobbyListRequest();
    }

    @FXML
    private void handleCreateLobbyAction(ActionEvent event) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(refreshButton.getScene().getWindow());
        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Insert gameSize"));
        Spinner<Integer> gameSizeSpinner = new Spinner<Integer>(2, 4, 2);
        dialogVbox.getChildren().add(gameSizeSpinner);
        Button confirmButton = new Button("Confirm");
        EventHandler<ActionEvent> confirmEvent = e -> {
            client.createLobby(gameSizeSpinner.getValue());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
            ((Node) (e.getSource())).getScene().getWindow().hide();
            gui.switchScene("Game.fxml");
        };
        confirmButton.setOnAction(confirmEvent);
        dialogVbox.getChildren().add(confirmButton);
        Scene dialogScene = new Scene(dialogVbox, 200, 150);
        dialog.setResizable(false);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
