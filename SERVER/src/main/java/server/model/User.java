package server.model;

import common.VirtualView;
import server.controller.TCPclientParser;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String userName;
    private boolean inGame = false;
    private boolean inLobby = false;
    private TCPclientParser myParser;

    private VirtualView myClient = null;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    public void setMyClient(VirtualView myClient) {
        this.myClient = myClient;
    }

    public VirtualView getMyClient() {
        return myClient;
    }

    @Override
    public String toString() {
        return "user:" + userName;
    }

    public TCPclientParser getMyParser() {
        return myParser;
    }

    public void setMyParser(TCPclientParser myParser) {
        this.myParser = myParser;
    }
}