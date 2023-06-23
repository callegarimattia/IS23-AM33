package server.model;
import server.controller.GameEnder;
import server.controller.GameHandler;
import server.controller.GameHandlerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Lobby {
    private static final AtomicInteger nextID = new AtomicInteger();
    private final List<User> lobbyUsers = new ArrayList<>();
    private final int ID;
    private GameHandlerImpl gameHandler;

    private final int gameSize;

    public Lobby(int gameSize) {
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

    public void add(User newUser) {  // ritorna true se deve essere startata
        lobbyUsers.add(newUser);
        newUser.setInLobby(true);
    }

    public void removeUser(User toBeRemovedUser) {
        toBeRemovedUser.setInLobby(false);
        lobbyUsers.remove(toBeRemovedUser);
    }

    public GameHandler initGame(GameEnder ender) {
        List<Player> players = new ArrayList<>();
        for (User user : lobbyUsers) {
            user.setInGame(true);
            user.setInLobby(false);
            if(user.getMyClient()!=null)
                players.add(new Player(user.getUserName(), user.getMyClient()));
            else
                players.add(new Player(user.getUserName(), user.getMyParser().getOut()));
        }
        Game myGame = new Game(players, ender);
        gameHandler = new GameHandlerImpl(myGame);
        return gameHandler;
    }


    public GameHandlerImpl getGameHandler() {
        return gameHandler;
    }
}
