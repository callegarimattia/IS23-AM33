package server.model.lobbies;

import server.model.gameLogic.InputException;
import server.model.gameLogic.LastRoundException;
import server.model.gameLogic.MainBoardCoordinates;

import java.util.List;

public interface GameHandlerInterface {

    boolean pickAndInsert(User user, List<MainBoardCoordinates> coordinates, int column) throws InputException, LastRoundException;
}
