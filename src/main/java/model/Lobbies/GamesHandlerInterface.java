package model.Lobbies;

import model.GameLogic.LastRoundException;
import model.GameLogic.PersonalGoals.PersonalGoalException;
import model.GameLogic.Tile;
import model.GameLogic.inputException;

import java.util.List;

public interface GamesHandlerInterface {

    boolean pickAndInsert(User user, List<Integer> xPos, List<Integer> yPos, int column) throws PersonalGoalException, inputException, LastRoundException;

}
