package server.controller;

import common.VirtualViewRMI;
import server.model.Lobby;
import server.model.User;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface LobbiesHandler {

    // interfaccia interna al server

    public List<String> createUser(String newUsername, VirtualViewRMI virtualView, Object parser);

    boolean isUserPresent(String username);

    User searchUser(String userName);;

    Lobby searchLobby(int ID);

    int createLobby(String firstUser, int gameSize);

    boolean joinLobby(String joiningUser, int lobbyID);

    boolean leaveLobby(String leavingUser);

    Set<Lobby> getWaitingLobbies();

    Set<User> getUsers();

    void startServer();

    void refresh();  // debug purpose only

    void addTCPparserToUser(String newUserUsername, TCPclientParser parser);

    void removeUser(String toBeRemovedUsername);

    void abortLobby(String userName);

    void disconnectedUser(String userName);

    public List<Integer> lobbyListRequest(VirtualViewRMI virtualView, Object parser);

}
