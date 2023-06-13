package com.example.gui.client.clientModel;

import com.example.gui.common.Tile;

public class ClientPlayer {
    String userName;
    Tile[][] myShelf;

    public ClientPlayer(String userName) {
        this.userName = userName;
        myShelf = new Tile[6][5];
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
