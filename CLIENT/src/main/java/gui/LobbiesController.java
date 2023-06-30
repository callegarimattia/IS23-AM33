package gui;
import client.Client;
import client.clientModel.Lobby;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbiesController {
    private Client client;

    @FXML
    private Button refreshButton;
    private GuiApplication gui;
    @FXML
    private ListView<Lobby> listView = new ListView<>();

    public void init(GuiApplication gui) {
        this.gui = gui;
        refreshLobbies();
        listView.setItems(client.getData().getLobbies());
        listView.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
        {
            if (e.isSecondaryButtonDown()) {
                e.consume();
            }
        });
        listView.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);

        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Lobby currentItemSelected = listView.getSelectionModel()
                        .getSelectedItem();
                if (currentItemSelected != null) {
                    client.joinLobby(currentItemSelected.getLobbyId());
                    try {
                        openGameWindow();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                listView.getSelectionModel().clearSelection();
            }
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleRefreshAction(ActionEvent event) {
        refreshLobbies();
    }

    private void refreshLobbies() {
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
            try {
                openGameWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
        confirmButton.setOnAction(confirmEvent);
        dialogVbox.getChildren().add(confirmButton);
        Scene dialogScene = new Scene(dialogVbox, 200, 150);
        dialog.setResizable(false);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void openGameWindow() throws IOException {
        refreshButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(LobbiesController.class.getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage thisStage = (Stage) refreshButton.getScene().getWindow();
        GameController gameController = fxmlLoader.getController();
        gameController.init(client);
        thisStage.setScene(scene);
        thisStage.setTitle("Game");
        thisStage.centerOnScreen();
        thisStage.setResizable(false);
        thisStage.setMaximized(true);
        thisStage.show();
    }
}
