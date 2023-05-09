package client;

import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.ListenerModel;
import server.listenerStuff.LobbiesUpdateEvent;
import server.model.Tile;
import server.rmi.ServerRMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientRMI_Impl extends UnicastRemoteObject implements ClientRMI, Client{
    private Tile[][] mainBoard = new Tile[9][9];
    private int indexCurrentPlayer = 0;
    private String userName;
    private String serverHost;
    private ServerRMI server;
    ListenerModel myListener;

    //...

    public ClientRMI_Impl(String serverHost, String userName, ListenerModel myListener) throws Exception {
        this.userName = userName;
        this.serverHost = serverHost;
        this.myListener = myListener;
        joinServer();
    }


    // RMI del server invocherà direttamente questo metodo
    @Override
    public void GameUpdate(GameUpdateEvent evt) {
        mainBoard = evt.getNewBoard();
        //...

    }

    @Override
    public void LobbiesUpdate(LobbiesUpdateEvent evt) {
        //...
    }

    public String newUserNameRequested() throws RemoteException{
        System.out.println("UserName already taken");
        Scanner scanner = new Scanner(System.in);
        userName = scanner.next();
        scanner.close();
        return userName;
    }

    private void joinServer() throws Exception {
        server = (ServerRMI) Naming.lookup("rmi://" + serverHost + "/Server");
        server.joinServer(myListener, userName, this);


    }

}
