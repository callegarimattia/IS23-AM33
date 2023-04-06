package model.lobbies;

import model.gameLogic.InputException;
import model.gameLogic.LastRoundException;
import model.gameLogic.MainBoardCoordinates;
import model.gameLogic.personalGoals.PersonalGoalException;

import java.util.List;

public interface GamesHandlerInterface {

    boolean pickAndInsert(User user, List<MainBoardCoordinates> coordinates, int column) throws PersonalGoalException, InputException, LastRoundException;
}
