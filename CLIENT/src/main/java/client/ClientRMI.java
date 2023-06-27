package client;
import client.clientModel.ClientDataStructure;
import common.ServerRMI;
import common.VirtualViewRMI;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.rmi.Naming;

public class ClientRMI extends UnicastRemoteObject implements Client, VirtualViewRMI {
    ServerRMI server;
    String username;
    int lobbyID;
    private int gameStatus = 1;  // da cambiare per ora è fisso
    Scanner scanner = new Scanner(System.in);
    private ClientDataStructure data;

    public ClientRMI() throws RemoteException {
    }

    @Override
    public ClientDataStructure getData() {
        return data;
    }

    @Override
    public void newConnection(String serverIP, int port) {
        try {
            server = (ServerRMI) Naming.lookup("rmi://" + serverIP + "/ServerRMI");
        } catch (NotBoundException | MalformedURLException| RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createUser(String username) {  // 0
        try {
            switch (server.createUser(username,this)){
                default:
                    System.out.println("non doveva succedere");
                    break;
                case -4:
                    System.out.println("can't create a new user, this client already has an associated User");
                    break;
                case -2:
                    System.out.println("invalid special username, press 0 and enter a new one: ");
                    break;
                case 0:
                    System.out.println("userName already taken, press 0 and enter a new one: ");
                    break;
                case 1:
                    this.username = username;
                    System.out.println("userName " + this.username + " successfully set");
                    break;
            }
        } catch (RemoteException e) {
            System.out.println("l errore è qui");
            System.out.println(e.getMessage());
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
                System.out.println("ServerRMI: the selected lobby can't be joined!");
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
            else System.out.println("ServerRMI: You are not in a lobby!");
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
                    System.out.println("ServerRMI: can't create a lobby while in game.");
                    return;
                }
                if (tmp == -1) {
                    System.out.println("ServerRMI: Username unknown -> ERROR");
                    System.exit(-1);
                }
                System.out.println("ServerRMI: game size invalid (only 2,3,4 are accepted)");
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
    public void setDisplayer(Thread th) {
        // tbd
    }

    public int getGameStatus() {
        return gameStatus;
    }



    @Override
    public void GameUpdate(List<String> players /* ...TBD...*/) throws RemoteException {

    }

    @Override
    public void LobbiesUpdate(List<String> players /* ...TBD...*/) throws RemoteException {

    }
}
