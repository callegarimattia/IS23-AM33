package server.controller;

import server.model.Lobby;
import server.model.User;

import java.util.Set;

public interface LobbiesHandler {

    // interfaccia interna al server
    boolean createUser(String newUsername);

    boolean isUserPresent(String username);

    User searchUser(String userName);
    boolean isLobbyPresent(int lobbyId);

    Lobby searchLobby(int ID);

    int createLobby(String firstUser, int gameSize);

    boolean joinLobby(String joiningUser, int lobbyID);

    boolean leaveLobby(String leavingUser);

    Set<Lobby> getWaitingLobbies();

    Set<Lobby> getInGameLobbies();

    Set<User> getUsers();

    void startServer();

    void refresh();  // debug purpose only

    void addTCPparserToUser(String newUserUsername, TCPclientParser parser);

    void removeUser(String toBeRemovedUsername);

    void abortLobby(String userName);

}