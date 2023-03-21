package model;

import java.util.ArrayList;
import java.util.List;


public class GamesHandler implements GamesHandlerInterface {

    private List<Lobby> inGameLobbies = new ArrayList<Lobby>();

    private LobbiesHandler lobbiesHandler;

    public void initGameHandler(LobbiesHandler lobbiesHandler) {
        this.lobbiesHandler = lobbiesHandler;
    }

    public void startGame(Lobby startingLobby) {
        inGameLobbies.add(startingLobby);
    }

    public void removeGame(Lobby toBeRemovedLobby) {
        inGameLobbies.remove(toBeRemovedLobby);
        lobbiesHandler.restoreLobby(toBeRemovedLobby);
    }

    @Override
    public boolean pickTiles(String playerUsername, int posX1, int posX2, int posY1, int posY2) {
        return false;
    }

    @Override
    public boolean insertTiles(String playerUsername, int column, Tile tile1, Tile tile2, Tile tile3) {
        return false;
    }
}
