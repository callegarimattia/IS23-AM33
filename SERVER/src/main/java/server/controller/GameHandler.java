package server.controller;

import org.json.simple.JSONObject;
import server.exceptions.InputException;
import server.exceptions.LastRoundException;
import common.MainBoardCoordinates;

import java.rmi.RemoteException;
import java.util.List;

public interface GameHandler {

    // interfaccia usata dal server
    JSONObject pickAndInsert(JSONObject obj);
    void abortGame(String disconnectedPlayer);
    void refresh();
    String getCurrPlayer();
    JSONObject sendChatMessage(JSONObject obj);
}
