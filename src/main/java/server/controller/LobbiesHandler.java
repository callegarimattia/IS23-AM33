package server.controller;

import server.model.Lobby;
import server.model.User;

import java.util.Set;

public interface LobbiesHandler {

    boolean createUser(String newUsername);

    boolean searchUser(String username);

    boolean searchLobby(int lobbyId);

    int createLobby(String firstUser, int gameSize);

    boolean joinLobby(String joiningUser, int lobbyID);

    boolean leaveLobby(String leavingUser);

    Set<Lobby> getLobbies();

    Set<User> getUsers();
}
