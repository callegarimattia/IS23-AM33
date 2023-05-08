package server.listenerStuff;

import client.ClientRMI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ListenerModel implements ModelUpdateListener {  //  Mailer

    // fintando di avere 4 player, 2 TCP e 2 RMI

    // lista di 2 socket/connessioni TCP
    // lista di connessioni RMI

    private List<ClientRMI> RMI_Lobby_clients = new ArrayList<>();

    @Override
    public void OnGameUpdate(GameUpdateEvent evt) {
        // cicla su lista RMI e invoca metodo GameUpdate sui ClientRMI
        // cicla su lista TCP e manda messaggio
    }

    @Override
    public void OnLobbyUpdate(LobbiesUpdateEvent evt) throws RemoteException {
        for(ClientRMI cl : RMI_Lobby_clients)
                cl.LobbiesUpdate(evt);
    }

    public void addRMI_Lobbies(ClientRMI newClient){
        RMI_Lobby_clients.add(newClient);
    }

    public void removeRMI_Lobbies(ClientRMI toBeRemovedClient){
        RMI_Lobby_clients.remove(toBeRemovedClient);
    }
}
