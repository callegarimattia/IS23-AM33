package client;

import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.ListenerModel;
import server.listenerStuff.LobbiesUpdateEvent;
import server.model.Tile;
import server.rmi.ServerRMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMI_Impl extends UnicastRemoteObject implements ClientRMI, Client{
    private Tile[][] mainBoard = new Tile[9][9];
    private int indexCurrentPlayer = 0;

    //...

    public ClientRMI_Impl(String serverHost) throws RemoteException {


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

    public void joinServer(String serverHost, ListenerModel myListener) throws Exception {
        ServerRMI server;
        server = (ServerRMI) Naming.lookup("rmi://" + serverHost + "/Server");
        server.setListener(myListener);
    }

}
