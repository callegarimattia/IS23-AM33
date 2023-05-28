package client;

import client.clientModel.ClientPlayer;
import server.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class ClientDataStructure {
    private Tile[][] mainboard;
    private List<ClientPlayer> players;

    private String myUsername;

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
        players = new ArrayList<>();
        mainboard = new Tile[9][9];
    }

    public void addPlayer(String username){
        players.add(new ClientPlayer(username));
    }

    public List<ClientPlayer> getPlayers() {
        return players;
    }

    public void setMainboard(Tile[][] mainboard) {
        this.mainboard = mainboard;
    }

    public Tile[][] getMainboard() {
        return mainboard;
    }

    //....
}
