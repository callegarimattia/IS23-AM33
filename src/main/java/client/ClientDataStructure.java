package client;

import server.model.Tile;

import java.util.List;

public class ClientDataStructure {
    private Tile[][] mainboard;
    private List<String> players;

    private String myUsername;

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    //....
}
