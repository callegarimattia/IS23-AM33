package controller;

public class Controller {
    private final LobbiesHandler lobbiesHandler;

    public Controller() {
        this.lobbiesHandler = new LobbiesHandler();
        //ListenerModel listener = new ListenerModel();
    }

    public LobbiesHandler getLobbiesHandler() {
        return lobbiesHandler;
    }
}
