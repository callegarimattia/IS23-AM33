package client;

import server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ClientRMI implements Client {
    private ClientDataStructure data;
    private int port = 1099;
    Registry registry;
    Server server;
    String username;
    int lobbyID;
    Scanner scanner = new Scanner(System.in);

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
    public void shutDown() {

    }


    @Override
    public void joinLobby(int lobbyID) {
        try {
            if (server.joinLobby(this.username, lobbyID)) this.lobbyID = lobbyID;
            else System.out.println("The selected lobby can't be joined!");
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
        int tmp;
        try {
            if ((tmp = server.createLobby(this.username, gameSize)) > 0) this.lobbyID = tmp;
            else if (tmp == -3) System.out.println("Server: Invalid game size!");
            else if (tmp == -2) System.out.println("Server: You can't create a lobby right now!");
            else if (tmp == -1) {
                System.out.println("Server: FATAL ERROR - USER UNKNOWN");
                System.exit(-1);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
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
    public void createUser() {

    }
}
