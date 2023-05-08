package server.listenerStuff;

import java.rmi.RemoteException;
import java.util.EventListener;

public interface ModelUpdateListener extends EventListener {
    void OnGameUpdate(GameUpdateEvent evt);

    void OnLobbyUpdate(LobbiesUpdateEvent evt) throws RemoteException;
}
