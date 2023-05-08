package client;
import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.LobbiesUpdateEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientRMI extends Remote{
    public void GameUpdate(GameUpdateEvent evt) throws RemoteException;
    public void LobbiesUpdate(LobbiesUpdateEvent evt) throws RemoteException;

}
