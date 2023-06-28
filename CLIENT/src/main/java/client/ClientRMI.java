package client;
import client.clientModel.ClientDataStructure;
import client.clientModel.Lobby;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.rmi.Naming;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class ClientRMI extends UnicastRemoteObject implements Client, VirtualViewRMI {
    ServerRMI server;
    String username = null;
    int lobbyID;
    CLI cli;
    Scanner scanner = new Scanner(System.in);
    private ClientDataStructure data = new ClientDataStructure();

    public ClientRMI(CLI cli) throws RemoteException {
        this.cli = cli;
        newConnection("localhost", 1099);
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
            System.out.print("server isn't online yet, closing application...");
            exit(-1);
        }
        Runnable r = new RMIchecker(server, cli);
        Thread th = new Thread(r);
        th.start();
    }

    @Override
    public void shutDown() { // -1
        try {
            List<String> mess = server.shutDownClient(this);
            String ans = mess.get(0);
            String user = null;
            if(ans.equals("2"))
                user = mess.get(1);
            data.ansClientClosingApp(ans, user);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createUser(String username) {  // 0
        try {
            List<String> ans = server.createUser(username,this, null);
            data.ansCreateUser(ans);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void LobbiesUpdate(List<Integer> lobbies) throws RemoteException {
        List<Long> longs = lobbies.stream()
                .mapToLong(Integer::longValue)
                .boxed().toList();
        data.onLobbyUpdate(longs);

    }


    @Override
    public void lobbyListRequest() {  // 1
        List<Integer> answer;
        try {
            answer =  server.lobbyListRequest(this, null);
            List<Long> longs = answer.stream()
                    .mapToLong(Integer::longValue)
                    .boxed().collect(Collectors.toList());
            data.ansLobbyListRequest(longs);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) {

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
    public void sendChatMessage(String text, String recipient) {

    }

    @Override
    public void setUserName(String userName) {

    }


    @Override
    public void GameUpdate(List<String> players /* ...TBD...*/) throws RemoteException {

    }



    @Override
    public boolean checkAlive() throws RemoteException {
        return true;
    }

}
