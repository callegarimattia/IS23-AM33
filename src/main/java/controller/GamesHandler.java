package controller;

import model.gameLogic.InputException;
import model.gameLogic.LastRoundException;
import model.gameLogic.MainBoardCoordinates;
import model.gameLogic.personalGoals.PersonalGoalException;
import model.lobbies.GamesHandlerInterface;
import model.lobbies.Lobby;
import model.lobbies.User;

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
    public boolean pickAndInsert(User user, List<MainBoardCoordinates> coordinates, int column) throws PersonalGoalException, InputException, LastRoundException {
        // controllo di univocità user tbd
        for (Lobby lobby : inGameLobbies) {
            List<User> users = lobby.getUsers();
            for (User iter : users)
                if (user.getUserName().equals(iter.getUserName())) {
                    //  forse sarebbe meglio non esporre game e avere il metodo picktiles su Lobby che lo chiama
                    //  a sua volta sua Game, non so
                    lobby.getGame().pickAndInsert(iter.getUserName(), coordinates, column);
                    return true;
                }
        }
        return false;
    }

}