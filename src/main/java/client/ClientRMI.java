package client;

import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.LobbiesUpdateEvent;
import server.model.Tile;
import server.controller.LobbiesHandlerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientRMI extends UnicastRemoteObject implements Client {
    private Tile[][] mainBoard = new Tile[9][9];
    private int indexCurrentPlayer = 0;
    private String userName;
    private String serverHost;
    private LobbiesHandlerInterface server;
    private int userID;

    //...

    public ClientRMI(String serverHost, String userName) throws Exception {
        this.userName = userName;
        this.serverHost = serverHost;
        joinServer();
        System.out.println(userID);
        while (!server.setUsername(userName, userID)){
            Scanner input = new Scanner(System.in);
            System.out.println("UserName not available, enter a new one: ");
            userName = input.next();
        }
        System.out.println("username setted");
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

    private void joinServer() {
        try{
            server = (LobbiesHandlerInterface) Naming.lookup("rmi://" + serverHost + "/LobbiesHandler");
        }
        catch (RemoteException | NotBoundException | MalformedURLException e){
            System.out.println("look-up failed, try again");
            return;
        }
        try{
            userID = server.joinServer(this);   // in realta gli sto solo passando il riferimento
        }
        catch (RemoteException e){
            System.out.println("server join failed, try again");
        }
    }

}
