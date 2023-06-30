package server.controller;

import org.json.simple.JSONObject;

public interface GameHandler {

    // interfaccia usata dal server
    JSONObject pickAndInsert(JSONObject obj);
    void abortGame(String disconnectedPlayer);
    void refresh();
    String getCurrPlayer();
    JSONObject sendChatMessage(JSONObject obj);
}
