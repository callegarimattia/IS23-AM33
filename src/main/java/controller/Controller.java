package controller;

import model.gameLogic.listenerStuff.ListenerModel;

public class Controller {
    private GamesHandler gamesHandler;
    private LobbiesHandler lobbiesHandler;

    public Controller() {
        this.gamesHandler = new GamesHandler();
        this.lobbiesHandler = new LobbiesHandler();
        gamesHandler.initGameHandler(lobbiesHandler);
        ListenerModel listener = new ListenerModel();
        lobbiesHandler.initLobbiesHandler(gamesHandler);
    }
}
