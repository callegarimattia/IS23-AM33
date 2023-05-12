package server.model;

import client.Client;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private String userName = null;
    private boolean inGame = false;
    private boolean inLobby = false;
    private Client myClient = null;

    private int ID;
    public User(String userName) {
        this.userName = userName;
    }

    public User(Client client) {
        myClient = client;
    }

    public User(User userCopy) {
        this.userName = userCopy.getUserName();
        this.inGame = userCopy.isInGame();
        this.inLobby = userCopy.isInLobby();
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

    public Client getMyClient() {
        return myClient;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}