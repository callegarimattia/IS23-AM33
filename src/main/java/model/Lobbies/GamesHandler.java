package model.Lobbies;

import model.GameLogic.PersonalGoals.PersonalGoalException;
import model.GameLogic.Tile;
import model.GameLogic.inputException;

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
    public boolean pickAndInsert(User user,List<Integer> xPos, List<Integer> yPos, int column) throws PersonalGoalException, inputException {
        // controllo di univocità user
        for (Lobby lobby : inGameLobbies){
            List<User> users = lobby.getUsers();
            for(User iter : users)
                if (user.getUserName().equals(iter.getUserName())){
                    //  forse sarebbe meglio non esporre game e avere il metodo picktiles su Lobby che lo chiama
                    //  a sua volta sua Game, non so
                    lobby.getGame().pickAndInsert(iter.getUserName(),yPos,xPos,column);
                    return true;
                }
        }
        return false;
    }

}
