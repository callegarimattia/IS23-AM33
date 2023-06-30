package client;

import client.clientModel.ClientDataStructure;

import java.util.List;

public interface Client {

    //  ha solo i metodi/comandi che chiama l utente

    ClientDataStructure getData();

    void newConnection(String serverIP, int port);

    void pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn);

    void shutDown();

    void createLobby(int gameSize);

    void joinLobby(int ID);

    void leaveLobby();

    void createUser(String userName);

    void lobbyListRequest();

    void sendChatMessage(String text, String recipient);

}
