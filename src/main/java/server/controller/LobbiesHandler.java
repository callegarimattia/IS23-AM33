package server.controller;

public interface LobbiesHandler {

    boolean createUser(String newUsername);

    boolean searchUser(String username);

    boolean searchLobby(int lobbyId);

    boolean createLobby(String firstUser, int gameSize);

    boolean joinLobby(String joiningUser, int lobbyID);

    boolean leaveLobby(String leavingUser);

}
