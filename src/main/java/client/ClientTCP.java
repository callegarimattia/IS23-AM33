package client;

import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.LobbiesUpdateEvent;

import java.rmi.RemoteException;

public class ClientTCP implements Client {
    @Override
    public void GameUpdate(GameUpdateEvent evt) throws RemoteException {

    }

    @Override
    public void LobbiesUpdate(LobbiesUpdateEvent evt) throws RemoteException {

    }
}
