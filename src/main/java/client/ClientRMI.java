package client;

import server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientRMI implements Client {
    Registry registry;
    Server server;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void newConnection(String serverIP) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
            server = (Server) registry
                    .lookup("Server");
            String newUsername = scanner.nextLine();
            while (!server.createUser(newUsername)) {
                //Just a test... (later we reiterate until a valid username is given)
                System.out.println("User already present!");
                newUsername = scanner.nextLine();
            }
            System.out.println(server.searchUser(newUsername));
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void joinLobby() {
        try {
            server.joinLobby("mattia", 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void joinLobby(int lobbyID) {

    }

    @Override
    public void leaveLobby() {

    }

    @Override
    public void createLobby(int gameSize) {

    }

    @Override
    public void sendMessage(String userName, String message, int visibility) {

    }
}
