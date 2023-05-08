package server.model;

import java.util.Objects;

public class User {
    private final String userName;
    private boolean inGame = false;
    private boolean inLobby = false;

    public User(String userName) {
        this.userName = userName;
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
}