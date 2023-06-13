package com.example.gui.client.clientModel;

import com.example.gui.client.Client;
import com.example.gui.client.ClientRMI;
import com.example.gui.client.ClientTCP;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class DataModel {
    private final ObservableList<Lobby> lobbyList = FXCollections.observableArrayList();
    private final ObjectProperty<Lobby> currentLobby = new SimpleObjectProperty<>(null);

    private Client client;

    public ObservableList<Lobby> getLobbyList() {
        return lobbyList;
    }

    public Lobby getCurrentLobby() {
        return currentLobby.get();
    }

    public ObjectProperty<Lobby> currentLobbyProperty() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby.set(currentLobby);
    }

    public void refreshLobby(){

    }
    public void createClientRMI(){
        client = new ClientRMI();
    }
    public void createClientTCP(){
        try {
            client = new ClientTCP();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Client getClient() {
        return client;
    }
}
