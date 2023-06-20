package server.controller;

import server.exceptions.InputException;
import server.exceptions.LastRoundException;
import server.model.MainBoardCoordinates;
import server.model.User;

import java.util.List;

public interface GameHandler {

    // interfaccia usata dal server
    int pickAndInsert(String userName, List<MainBoardCoordinates> coordinates, int column) throws InputException, LastRoundException;
    void abortGame(String disconnectedPlayer);
    void refresh();
    String getCurrPlayer();
    int chatMessage(String text, String recipient, String addresser);
}
