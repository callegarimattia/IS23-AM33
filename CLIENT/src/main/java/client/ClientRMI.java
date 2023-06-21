package client;

import client.clientModel.ClientDataStructure;
import common.Server;
import common.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class ClientRMI implements Client, VirtualView {
    Registry registry;
    Server server;
    String username;
    int lobbyID;
    Scanner scanner = new Scanner(System.in);
    private ClientDataStructure data;

    @Override
    public ClientDataStructure getData() {
        return data;
    }

    @Override
    public void newConnection(String serverIP, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            server = (Server) registry
                    .lookup("Server");
            System.out.println("Connection established!");
            System.out.println("Insert a new username:");
            String newUsername = scanner.nextLine();
            while (!server.createUser(newUsername)) {
                System.out.println("Username not available, please provide another one: ");
                newUsername = scanner.nextLine();
            }
            this.username = newUsername;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) {

    }


    @Override
    public void shutDown() {

    }


    @Override
    public void joinLobby(int ID) {  // DA RIFARE
        try {
            System.out.println("Client: provide a lobbyID to be joined");
            int lobbyID = scanner.nextInt();
            while (server.joinLobby(this.username, lobbyID)) {
                System.out.println("Server: the selected lobby can't be joined!");
                System.out.println("Client: provide a different lobbyID or -1 to exit");
                lobbyID = scanner.nextInt();
                if (lobbyID == -1) return;
            }
            this.lobbyID = lobbyID;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void leaveLobby() {
        try {
            if (server.leaveLobby(this.username)) this.lobbyID = -1;
            else System.out.println("Server: You are not in a lobby!");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createLobby(int gameSize) {
     /*   int tmp;
        try {
            System.out.println("Client: insert a gameSize (2,3,4 are accepted):");
            int gameSize = scanner.nextInt();
            while ((tmp = server.createLobby(this.username, gameSize)) < 0) {
                if (tmp == -2) {
                    System.out.println("Server: can't create a lobby while in game.");
                    return;
                }
                if (tmp == -1) {
                    System.out.println("Server: Username unknown -> ERROR");
                    System.exit(-1);
                }
                System.out.println("Server: game size invalid (only 2,3,4 are accepted)");
                System.out.println("Client: please provide a new game size");
                gameSize = scanner.nextInt();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } */
    }

    @Override
    public void sendChatMessage(String userName, String message, int visibility) {

    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public void lobbyListRequest() {
    }

    @Override
    public void sendChatMessage(String text, String recipient) {

    }

    @Override
    public void createUser(String username) {

    }

    @Override
    public void GameUpdate(List<String> players /* ...TBD...*/) throws RemoteException {

    }

    @Override
    public void LobbiesUpdate(List<String> players /* ...TBD...*/) throws RemoteException {

    }
}
