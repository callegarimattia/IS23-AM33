package model.lobbies;

import model.gameLogic.InputException;
import model.gameLogic.LastRoundException;
import model.gameLogic.MainBoardCoordinates;

import java.util.List;

public interface GameHandlerInterface {

    boolean pickAndInsert(User user, List<MainBoardCoordinates> coordinates, int column) throws InputException, LastRoundException;
}
