package server.model;

import server.controller.GameHandlerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Lobby {
    private static final AtomicInteger nextID = new AtomicInteger();
    private final List<User> lobbyUsers = new ArrayList<>();
    private final int ID;

    // private Game game;   non serve averlo tra gli attributi, ci accedo solo tramite gameHandler

    private GameHandlerImpl gameHandlerImpl;

    private final int gameSize;

    public Lobby(Lobby lobbyCopy) {
        this.ID = lobbyCopy.getID();
        this.gameSize = lobbyCopy.getGameSize();
        for (User user : lobbyCopy.getUsers()) {
            lobbyUsers.add(new User(user));
        }
    }

    public Lobby(User firstUser, int gameSize) {
        this.lobbyUsers.add(firstUser);
        firstUser.setInLobby(true);
        this.ID = nextID.incrementAndGet();
        this.gameSize = gameSize;
    }

    public int getGameSize() {   // da cancellare, inutile ma ce in un test
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

    public boolean add(User newUser) {  // ritorna true se deve essere startata
        lobbyUsers.add(newUser);
        newUser.setInLobby(true);
        if(lobbyUsers.size()==gameSize)
            return true;
        return false;
    }

    public void remove(User toBeRemovedUser) {
        lobbyUsers.remove(toBeRemovedUser);
        toBeRemovedUser.setInLobby(false);
    }

    public void initGame() {
        List<Player> players = new ArrayList<>();
        for (User user : lobbyUsers) {
            user.setInGame(true);
            user.setInLobby(false);
            players.add(new Player(user.getUserName(), user.getMyClient()));
        }
        Game myGame = new Game(players);
        gameHandlerImpl = new GameHandlerImpl(myGame);
    }

    // quando il game sarà finito devo cancellare gli users e la lobby da lobbyHandler

    public GameHandlerImpl getGameHandler() {
        return gameHandlerImpl;
    }
}
