package client;

public interface Client {

    //  ha solo i metodi/comandi che chiama l utente

    void newConnection(String serverIP, int port);

    void pickAndInsert();

    void shutDown();
    void createUser();

    void joinLobby();

    void leaveLobby();

    void createLobby();

    void sendChatMessage(String userName, String message, int visibility);
    String getUserName();
    void lobbyListRequest();

}
