package client;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Client {

    //  ha solo i metodi/comandi che chiama l utente

    void newConnection(String serverIP, int port);

    //void pickAndInsert()


    void createUser(String newUsername);
    void joinLobby(int lobbyID);

    void leaveLobby();

    void createLobby(int gameSize);

    void sendChatMessage(String userName, String message, int visibility);

}
