package com.example.gui.client.listenerStuff;


import com.example.gui.common.Tile;

import java.util.EventObject;

public class GameUpdateEvent extends EventObject {
    private final Tile[][] newShelf;
    private final Tile[][] newBoard;
    private final String username;


    public GameUpdateEvent(Object source, Tile[][] shelf, Tile[][] board, String username) {
        super(source);
        this.newShelf = shelf;
        this.newBoard = board;
        this.username = username;
    }

    public Tile[][] getNewShelf() {
        return newShelf;
    }

    public Tile[][] getNewBoard() {
        return newBoard;
    }

    public String getUsername() {
        return username;
    }
}
