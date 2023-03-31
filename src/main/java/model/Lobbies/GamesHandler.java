package model.Lobbies;

import model.GameLogic.Tile;

import java.util.ArrayList;
import java.util.List;


public class GamesHandler implements GamesHandlerInterface {

    private List<Lobby> inGameLobbies = new ArrayList<>();

    private LobbiesHandler lobbiesHandler;

    public void initGameHandler(LobbiesHandler lobbiesHandler) {
        this.lobbiesHandler = lobbiesHandler;
    }

    public void startGame(Lobby startingLobby) {
        inGameLobbies.add(startingLobby);
        startingLobby.initGame();
    }

    public void removeGame(Lobby toBeRemovedLobby) {
        inGameLobbies.remove(toBeRemovedLobby);
        lobbiesHandler.restoreLobby(toBeRemovedLobby);
    }

    @Override
    public boolean pickTiles(User user, int posX1, int posX2, int posY1, int posY2) {
        // cerca utente tra le lobby e trova il game
        //
        // chiama metodo di game (aggiorna il modello)

        return false;
    }

    @Override
    public boolean insertTiles(User user, int column, Tile tile1, Tile tile2, Tile tile3) {
        return false;
    }
}
