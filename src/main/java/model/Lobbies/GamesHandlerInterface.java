package model.Lobbies;

import model.GameLogic.LastRoundException;
import model.GameLogic.MainBoardCoordinates;
import model.GameLogic.PersonalGoals.PersonalGoalException;
import model.GameLogic.Tile;
import model.GameLogic.inputException;

import java.util.List;

public interface GamesHandlerInterface {

    boolean pickAndInsert(User user, List<MainBoardCoordinates> coordinates, int column) throws PersonalGoalException, inputException, LastRoundException;
}
