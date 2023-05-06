package controller;

import model.lobbies.LobbiesHandlerInterface;

public class Controller {
    private final LobbiesHandlerInterface lobbiesHandler;

    public Controller() {
        this.lobbiesHandler = new LobbiesHandler();
        //ListenerModel listener = new ListenerModel();
    }

}
