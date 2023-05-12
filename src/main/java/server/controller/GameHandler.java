package server.controller;

import server.exceptions.InputException;
import server.exceptions.LastRoundException;
import server.model.MainBoardCoordinates;
import server.model.User;

import java.util.List;

public interface GameHandler {

    boolean pickAndInsert(User user, List<MainBoardCoordinates> coordinates, int column) throws InputException, LastRoundException;
}
