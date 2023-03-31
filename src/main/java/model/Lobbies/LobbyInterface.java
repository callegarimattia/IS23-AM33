package model.Lobbies;

public interface LobbyInterface {
    void createUser(String newUsername) throws LobbiesHandlerException;

    void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException;

    void joinLobby(User joiningUser, Lobby toBeJoinedlobby) throws LobbiesHandlerException;

    void leaveLobby(User leavingUser) throws LobbiesHandlerException;

}
