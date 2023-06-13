package com.example.gui;

import com.example.gui.client.clientModel.DataModel;
import com.example.gui.client.clientModel.Lobby;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LobbyListController {
    @FXML
    private ListView<Lobby> listBoxMain;

    private DataModel model;

    @FXML
    private void refreshList(ActionEvent action){

    }

    @FXML
    private void createLobby(ActionEvent action){

    }

    public void initModel(DataModel model) {
        // ensure model is only set once:
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model ;
        listBoxMain.setItems(model.getLobbyList());

        listBoxMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                model.setCurrentLobby(newSelection));

        model.currentLobbyProperty().addListener((obs, oldPerson, newPerson) -> {
            if (newPerson == null) {
                listBoxMain.getSelectionModel().clearSelection();
            } else {
                listBoxMain.getSelectionModel().select(newPerson);
            }
        });
    }
}
