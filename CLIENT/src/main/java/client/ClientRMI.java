package client;
import client.clientModel.ClientDataStructure;
import client.clientModel.Lobby;
import common.ServerRMI;
import common.VirtualViewRMI;
import org.json.simple.JSONObject;

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
    public void LobbiesUpdate(JSONObject obj) throws RemoteException {  // 99
        try {
            JSONObject answer =  server.lobbyListRequest(this, null);
            List<Integer> lobbiesIDs = (List<Integer>) answer.get("IDs");
            List<Integer> lobbiesCurrentSize = (List<Integer>) answer.get("CurrentSizes");
            List<Integer> lobbiesMaxSizes = (List<Integer>) answer.get("MaxSizes");
            List<Long> lobbiesIDs2 = new ArrayList<>(), lobbiesCurrentSize2 = new ArrayList<>(), lobbiesMaxSizes2 = new ArrayList<>();
            if(lobbiesIDs != null)
                for (int i=0;i<lobbiesIDs.size();++i) {
                    lobbiesIDs2.add(lobbiesIDs.get(i).longValue());
                    lobbiesCurrentSize2.add(lobbiesCurrentSize.get(i).longValue());
                    lobbiesMaxSizes2.add(lobbiesMaxSizes.get(i).longValue());
                }
            answer.put("IDs", lobbiesIDs2);
            answer.put("CurrentSizes", lobbiesCurrentSize2);
            answer.put("MaxSizes", lobbiesMaxSizes2);
            data.onLobbyUpdate(answer);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void lobbyListRequest() {  // 1
        try {
            JSONObject answer =  server.lobbyListRequest(this, null);
            LobbiesUpdate(answer);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createLobby(int gameSize) {  // 2
        try {
            List<Integer> ans = server.createLobby(gameSize,this,null);
            List<Long> longs = ans.stream()
                    .mapToLong(Integer::longValue)
                    .boxed().collect(Collectors.toList());
            data.ansNewLobbyCreation(longs);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void joinLobby(int ID) {   // 3
        try {
            int ans = server.joinLobby(ID,this,null);
            data.ansJoinLobbyRequest(ans, ID);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) {

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
