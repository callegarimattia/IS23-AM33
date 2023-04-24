package model.gameLogic;

import model.gameLogic.personalGoals.PersonalGoalException;
import model.lobbies.GamesHandlerInterface;
import model.lobbies.Lobby;
import model.lobbies.User;

import java.util.List;


public class GameHandler implements GamesHandlerInterface {

    private Lobby lobby;

    @Override
    public boolean pickAndInsert(User turnUser, List<MainBoardCoordinates> coordinates, int column) throws PersonalGoalException, InputException, LastRoundException {
        for (User user : lobby.getUsers()) {
            if (turnUser.getUserName().equals(user.getUserName())) {
                //  forse sarebbe meglio non esporre game e avere il metodo picktiles su Lobby che lo chiama
                //  a sua volta sua Game, non so
                return lobby.getGame().pickAndInsert(user.getUserName(), coordinates, column);
            }
        }
        return false;
    }
}
