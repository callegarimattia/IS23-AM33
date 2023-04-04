package controller;

import model.GameLogic.PersonalGoals.PersonalGoalException;
import model.GameLogic.inputException;
import model.Lobbies.GamesHandler;
import model.Lobbies.LobbiesHandler;
import model.Lobbies.Lobby;
import model.Lobbies.User;

import java.util.List;

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
