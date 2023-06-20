package client;

import common.listenerStuff.GameUpdateEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface VirtualView extends Remote {

    //  ha solo i metodi che chiama il server

    public void GameUpdate(GameUpdateEvent evt) throws RemoteException;

    //public void LobbiesUpdate(LobbiesUpdateEvent evt) throws RemoteException;

}
