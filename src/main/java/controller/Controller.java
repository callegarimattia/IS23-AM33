package controller;

import model.Lobbies.GamesHandler;
import model.Lobbies.LobbiesHandler;

public class Controller {
    private GamesHandler gamesHandler;
    private LobbiesHandler lobbiesHandler;

    public Controller() {
        this.gamesHandler = new GamesHandler();
        this.lobbiesHandler = new LobbiesHandler();
        gamesHandler.initGameHandler(lobbiesHandler);
        lobbiesHandler.initLobbiesHandler(gamesHandler);
    }
}
