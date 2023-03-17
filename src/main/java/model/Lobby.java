package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Lobby {
    static AtomicInteger nextID = new AtomicInteger();
    private final ArrayList<User> lobbyUsers = new ArrayList<>();
    private final int ID;
    private Game game;
    private boolean inGame = false;
    private final int gameSize;

    public Lobby(User firstUser, int gameSize) {
        this.lobbyUsers.add(firstUser);
        firstUser.setInLobby(true);
        this.ID = nextID.incrementAndGet();
        this.gameSize = gameSize;
    }

    public Game getGame() {
        return game;
    }

    public boolean isInGame() {
        return inGame;
    }

    public int getGameSize() {
        return gameSize;
    }

    public List<User> getUsers() {
        return lobbyUsers;
    }

    public boolean isFull() {
        return lobbyUsers.size() == gameSize;
    }

    public int getID() {
        return ID;
    }

    public void add(User newUser) {
        lobbyUsers.add(newUser);
        newUser.setInLobby(true);
    }

    public void remove(User toBeRemovedUser) {
        lobbyUsers.remove(toBeRemovedUser);
        toBeRemovedUser.setInLobby(false);
    }
}
