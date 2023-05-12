package client;

public interface Client {
    void newConnection();

    //void pickAndInsert();

    void joinLobby(int lobbyID);

    void leaveLobby();

    void createLobby(int gameSize);

    void sendMessage(String userName, String message, int visibility);

}
